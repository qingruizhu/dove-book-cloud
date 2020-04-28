package com.dove.sso.controller;

import com.dove.common.base.annotation.CommonResultAnnon;
import com.dove.common.base.vo.CommonResult;
import com.dove.common.shiro.credential.ShiroUser;
import com.dove.sso.enm.SsoErrorEnum;
import com.dove.sso.exception.SsoException;
import com.dove.sso.service.SsoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import static com.dove.common.shiro.filter.JwtAuthenFilter.AUTHORIZATION_HEADER;
import static com.dove.common.shiro.filter.JwtAuthenFilter.JWT_AUTHZSCHEME;

@CommonResultAnnon
@RestController
//@RequestMapping("/sso")
public class SsoController {

    private Logger logger = LoggerFactory.getLogger(SsoController.class);

    @Resource
    private SsoService service;


    @GetMapping(value = "/test")
    @RequiresPermissions("query")
    @RequiresRoles({"user"})
    public CommonResult test() {
        return CommonResult.success("哈哈哈");
    }

    /**
     * 用户名密码登录
     */
    @GetMapping(value = "/login")
    public CommonResult login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletResponse response) {
        try {
            String token = service.login(username, password);
            response.setHeader(AUTHORIZATION_HEADER, JWT_AUTHZSCHEME + " " + token);
            return CommonResult.success();
        } catch (Exception e) {
            logger.error("【{}】登录失败", username);
            throw new SsoException(SsoErrorEnum.SSO_LOGIN_ERROR_USER);
        }
    }


    /**
     * 手机登录
     *
     * @param phone    手机号
     * @param authCode 验证码
     */
    @GetMapping("/loginByPhone")
    public CommonResult loginByPhone(
            @RequestParam("phone") String phone,
            @RequestParam("authCode") String authCode,
            HttpServletResponse response) {
        String token = service.loginByPhone(phone, authCode);
        response.setHeader(AUTHORIZATION_HEADER, JWT_AUTHZSCHEME + " " + token);
        return CommonResult.success();
    }

    /**
     * 退出登录
     */
    @GetMapping(value = "/logout")
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipals() != null) {
            Object primaryPrincipal = subject.getPrincipals().getPrimaryPrincipal();
            ShiroUser user = (ShiroUser) primaryPrincipal;
//            userService.deleteLoginInfo(user.getUsername());
        }
        subject.logout();
    }

}
