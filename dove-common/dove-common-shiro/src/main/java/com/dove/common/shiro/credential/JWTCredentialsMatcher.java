package com.dove.common.shiro.credential;

import com.dove.common.shiro.dto.User;
import com.dove.common.shiro.util.JwtTokenUtil;
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
        String salt = authenticationInfo.getCredentials().toString();
        User user = (User) authenticationInfo.getPrincipals().getPrimaryPrincipal();
        try {
//            Algorithm algorithm = Algorithm.HMAC256(salt);
//            JWTVerifier verifier = JWT.require(algorithm)
//                    .withClaim("username", user.getUsername())
//                    .build();
//            verifier.verify(token);

            return jwtTokenUtil.verify(token, user.getId().toString());
        } catch (Exception e) {
            log.error("Token Error:{}", e.getMessage());
            return false;
        }
    }

}
