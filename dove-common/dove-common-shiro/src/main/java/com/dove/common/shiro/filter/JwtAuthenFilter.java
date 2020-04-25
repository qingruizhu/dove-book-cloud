package com.dove.common.shiro.filter;

import cn.hutool.json.JSONUtil;
import com.dove.common.base.vo.CommonResult;
import com.dove.common.shiro.dto.User;
import com.dove.common.shiro.token.JWTToken;
import com.dove.common.shiro.util.JwtTokenUtil;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import static com.dove.common.shiro.enm.ShiroErrorEnum.AUTHEN_ERROR_TOKEN;

public class JwtAuthenFilter extends BasicHttpAuthenticationFilter {
    private final Logger log = LoggerFactory.getLogger(JwtAuthenFilter.class);

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String JWT_AUTHZSCHEME = "Bearer";

    private static final int tokenRefreshInterval = 300;
    private JwtTokenUtil jwtTokenUtil;

    public JwtAuthenFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.setLoginUrl("/login");
    }

    /**
     * 1.前置
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name()))
            //对于OPTION请求做拦截 -> 不做token校验
            return false;
        return super.preHandle(request, response);
    }

    /**
     * 2.验证
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (this.isLoginRequest(request, response)) return true;//登录验证直接放行
        Boolean afterFiltered = (Boolean) (request.getAttribute("jwtShiroFilter.FILTERED"));
        if (BooleanUtils.isTrue(afterFiltered)) return true;

        if (!this.isJwtAttempt(request, response)) return false;
        boolean allowed = false;
        try {
            // 进行Shiro的JWTShiroRealm
            allowed = this.executeLogin(request, response);
        } catch (IllegalStateException e) { //not found any token
            log.error("Not found any token");
        } catch (Exception e) {
            log.error("Error occurs when login", e);
        }
        return allowed || super.isPermissive(mappedValue);
    }

    /**
     * 3.尝试验证->主要校验token是否存在
     */
    private boolean isJwtAttempt(ServletRequest request, ServletResponse response) {
        String authzHeader = this.getAuthzHeader(request);
        if (null == authzHeader) {
            log.error("token 不存在");
            return false;
        }
        String scheme = JWT_AUTHZSCHEME.toLowerCase(Locale.ENGLISH);
        return authzHeader.toLowerCase(Locale.ENGLISH).startsWith(scheme);
    }


//    @Override
//    protected String getAuthzHeader(ServletRequest request) {
//        HttpServletRequest httpRequest = WebUtils.toHttp(request);
//        String header = httpRequest.getHeader(AUTHORIZATION_HEADER);
//        return StringUtils.removeStart(header, "Bearer ");
//    }

    /**
     * 4.{@link #executeLogin(ServletRequest, ServletResponse)}
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        String allToken = this.getAuthzHeader(servletRequest);
        String jwtToken = StringUtils.removeStart(allToken, JWT_AUTHZSCHEME).trim();
        if (JwtTokenUtil.isTokenExpired(jwtToken)) return null;
        return new JWTToken(jwtToken);
    }

    /**
     * 5.成功
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        if (token instanceof JWTToken) {
            JWTToken jwtToken = (JWTToken) token;
            User user = (User) subject.getPrincipal();
            boolean shouldRefresh = shouldTokenRefresh(JwtTokenUtil.getIssuedAt(jwtToken.getToken()));
            if (shouldRefresh) {
                String newToken = jwtTokenUtil.sign(user.getId().toString());
                if (StringUtils.isNotBlank(newToken)) httpResponse.setHeader("x-auth-token", newToken);
            }
        }
        return true;
    }

    private boolean shouldTokenRefresh(Date issueAt) {
        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().minusSeconds(tokenRefreshInterval).isAfter(issueTime);
    }

    /**
     * 6.登录验证失败后进行
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        log.error("Validate token fail, token:{}, error:{}", token.toString(), e.getMessage());
        return false;
    }


    /**
     * 7.验证不通过后执行
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        log.error("token 校验失败");
        HttpServletResponse httpResponse = WebUtils.toHttp(servletResponse);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=UTF-8");
//        httpResponse.setStatus(HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION);
        fillCorsHeader(WebUtils.toHttp(servletRequest), httpResponse);
        CommonResult failed = CommonResult.failed(AUTHEN_ERROR_TOKEN.getCode(), AUTHEN_ERROR_TOKEN.getMessage());
        httpResponse.getWriter().print(JSONUtil.toJsonStr(failed));
        return false;
    }

    /**
     * 8.后置
     */
    @Override
    protected void postHandle(ServletRequest request, ServletResponse response) {
        this.fillCorsHeader(WebUtils.toHttp(request), WebUtils.toHttp(response));
        request.setAttribute("jwtShiroFilter.FILTERED", true);
    }

    /**
     * 8.设置跨域
     */
    private void fillCorsHeader(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,HEAD");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
    }
}
