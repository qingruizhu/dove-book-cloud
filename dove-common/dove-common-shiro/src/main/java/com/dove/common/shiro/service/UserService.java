package com.dove.common.shiro.service;

import com.dove.common.shiro.dto.User;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/23 19:36
 */
@Service
public class UserService implements IUserService {
    private static final String encryptSalt = "F12839WhsnnEV$#23b";

    @Override
    public User getUserInfo(String username) {
        User user = new User();
        user.setId((long) 123);
        user.setUsername("zqr");
        user.setPassword(new Sha256Hash("123456", encryptSalt).toHex());
        HashSet<String> roles = new HashSet<>();
        roles.add("admin");
        roles.add("user");
        user.setRoles(roles);
        HashSet<String> permissions = new HashSet<>();
        permissions.add("add");
        permissions.add("query");
        user.setPermissions(permissions);

        return user;
    }

    @Override
    public User getUserInfo(Long uid) {
        User user = new User();
        user.setId((long) 123);
        user.setUsername("zqr");
        user.setPassword(new Sha256Hash("123456", encryptSalt).toHex());
        HashSet<String> roles = new HashSet<>();
        roles.add("admin");
        roles.add("user");
        user.setRoles(roles);
        HashSet<String> permissions = new HashSet<>();
        permissions.add("add");
        permissions.add("query");
        user.setPermissions(permissions);
        return user;
    }

    @Override
    public Set<String> getUserRoles(Long uid) {
        HashSet<String> roles = new HashSet<>();
        roles.add("admin2");
        roles.add("user2");
        return roles;
    }

    @Override
    public Set<String> getUserPermissions(Long uid) {
        HashSet<String> permissions = new HashSet<>();
        permissions.add("add2");
        permissions.add("query2");
        return permissions;
    }
}
