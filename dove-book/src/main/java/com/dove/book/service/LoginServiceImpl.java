package com.dove.book.service;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/20 23:12
 */

import com.dove.book.bgd.model.Permission;
import com.dove.book.bgd.model.Role;
import com.dove.book.bgd.model.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public User getUserByName(String username) {
        //模拟数据库查询，正常情况此处是从数据库或者缓存查询。
        return getMapByName(username);
    }

    private User getMapByName(String username) {
        User user = new User();
        user.setId((long) 1);
        user.setName("zqr");
        user.setPassword("zqr");

        HashSet<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setId((long) 1);
        role.setName("admin");
        HashSet<Permission> permissions = new HashSet<>();
        Permission permission = new Permission();
        permission.setId((long) 1);
        permission.setName("query");
        permissions.add(permission);
        role.setPermissions(permissions);
        roles.add(role);
        user.setRoles(roles);
        return user;
    }
}