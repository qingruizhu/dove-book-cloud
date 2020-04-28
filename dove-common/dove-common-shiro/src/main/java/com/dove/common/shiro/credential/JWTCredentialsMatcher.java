package com.dove.common.shiro.credential;

import com.dove.common.jwt.util.JwtTokenUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JWTCredentialsMatcher implements CredentialsMatcher {

    private final Logger log = LoggerFactory.getLogger(JWTCredentialsMatcher.class);

    private JwtTokenUtil jwtTokenUtil;

    public JWTCredentialsMatcher(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        String token = (String) authenticationToken.getCredentials();
        ShiroUser user = (ShiroUser) authenticationInfo.getPrincipals().getPrimaryPrincipal();
        return jwtTokenUtil.verify(token, user.getUsername());
    }

}
