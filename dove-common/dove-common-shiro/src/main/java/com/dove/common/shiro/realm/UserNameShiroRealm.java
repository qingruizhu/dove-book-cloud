package com.dove.common.shiro.realm;

import com.dove.common.shiro.credential.ShiroUser;
import com.dove.common.shiro.service.UserShiroService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class UserNameShiroRealm extends AuthenticatingRealm {
    private final Logger log = LoggerFactory.getLogger(UserNameShiroRealm.class);

    @Value("${shiro.pwd.salt:bmljYWljYWlrYW4=}")
    private String pwdSalt;

    @Autowired(required = false)
    private UserShiroService userShiroService;

    public UserNameShiroRealm() {
        this.setCredentialsMatcher(new HashedCredentialsMatcher(Sha256Hash.ALGORITHM_NAME));
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userpasswordToken = (UsernamePasswordToken) token;
        String username = userpasswordToken.getUsername();
        ShiroUser shiroUser = userShiroService.getUserInfo(username);
        if (shiroUser == null)
            throw new AuthenticationException("用户不存在");
        return new SimpleAuthenticationInfo(shiroUser, shiroUser.getPassword(), ByteSource.Util.bytes(pwdSalt), this.getName());
    }

}
