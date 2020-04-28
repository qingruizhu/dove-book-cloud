package com.dove.common.shiro.realm;

import com.dove.common.jwt.util.JwtTokenUtil;
import com.dove.common.shiro.credential.JWTCredentialsMatcher;
import com.dove.common.shiro.credential.ShiroUser;
import com.dove.common.shiro.service.UserShiroService;
import com.dove.common.shiro.token.JWTToken;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.Set;


/**
 * 自定义身份认证
 * 基于HMAC（ 散列消息认证码）的控制域
 */

public class JwtShiroRealm extends AuthorizingRealm {

    @Resource
    private UserShiroService userService;

    public JwtShiroRealm(JWTCredentialsMatcher matcher) {
        this.setCredentialsMatcher(matcher);
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        JWTToken jwtToken = (JWTToken) authcToken;
        String token = jwtToken.getToken();

        ShiroUser shiroUser = userService.getUserInfo(JwtTokenUtil.getSubject(token));
        if (shiroUser == null)
            throw new AuthenticationException("token失效，请重新登录");

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(shiroUser, token, this.getName());

        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        ShiroUser user = (ShiroUser) principals.getPrimaryPrincipal();
        Set<String> roles = user.getRoles();
        if (CollectionUtils.isEmpty(roles)) {
            roles = userService.getUserRoles(user.getId());
        }
        if (roles != null) {
            simpleAuthorizationInfo.setRoles(roles);
        }
        Set<String> permissions = user.getPermissions();
        if (CollectionUtils.isEmpty(permissions)) {
            permissions = userService.getUserPermissions(user.getId());
        }
        if (permissions != null) {
            simpleAuthorizationInfo.setStringPermissions(permissions);
            user.setPermissions(permissions);
        }
        return simpleAuthorizationInfo;
    }
}
