package com.dove.sso.dao;

import com.dove.book.bgd.model.Permission;
import com.dove.book.bgd.model.Role;

import java.util.List;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/28 10:20
 */
public interface SsoDao {
    /**
     * userId -> 角色
     */
    List<Role> getRolesByUserId(Long userId);

    /**
     * roleId -> 权限
     */
    List<Permission> getPermissionsByRoleId(Long RoleId);

    /**
     * userId -> 权限
     */
    List<Permission> getPermissionsByUserId(Long userId);
}
