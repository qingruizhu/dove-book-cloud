package com.dove.common.shiro.realm;

import com.dove.common.shiro.dto.User;
import com.dove.common.shiro.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DbShiroRealm extends AuthenticatingRealm {
    private final Logger log = LoggerFactory.getLogger(DbShiroRealm.class);

    private static final String encryptSalt = "F12839WhsnnEV$#23b";

    @Autowired(required = false)
    private IUserService userService;

    public DbShiroRealm() {
//        this.userService = userService;
//        new Sha256Hash("123456", encryptSalt).toHex()
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
        User user = userService.getUserInfo(username);
        if (user == null)
            throw new AuthenticationException("用户不存在");
        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(encryptSalt), "dbShiroRealm");
    }

}
