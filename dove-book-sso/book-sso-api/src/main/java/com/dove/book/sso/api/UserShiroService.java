package com.dove.book.sso.api;

import com.dove.book.bgd.model.Permission;
import com.dove.book.bgd.model.Role;
import com.dove.book.bgd.model.User;
import com.dove.book.bgd.service.AbsUserService;
import com.dove.book.sso.api.dao.SsoDao;
import com.dove.book.sso.api.dto.UserDto;
import com.dove.common.shiro.core.credential.ShiroUser;
import com.dove.common.shiro.core.service.IUserShiroService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/23 19:36
 */
//@Service
public class UserShiroService extends AbsUserService<UserDto> implements IUserShiroService {
    @Resource
    private SsoDao ssoDao;

    @Override
    public ShiroUser getUserInfo(String username) {
        if (StringUtils.isBlank(username)) return null;
        User query = new User();
        query.setUsername(username);
        User user = this.select(query);
        if (null == user) return null;
        return convertShiroUser(user);
    }

    private ShiroUser convertShiroUser(User user) {
        ShiroUser shiroUser = new ShiroUser();
        shiroUser.setId(user.getId());
        shiroUser.setUsername(user.getUsername());
        shiroUser.setPassword(user.getPassword());
        shiroUser.setPwdSalt(user.getPwdSalt());
        shiroUser.setRoles(this.getUserRoles(user.getId()));
        shiroUser.setPermissions(this.getUserPermissions(user.getId()));
        return shiroUser;
    }

    @Override
    public ShiroUser getUserInfoByPhone(String phone) {
        if (null == phone) return null;
        User query = new User();
        query.setPhone(phone);
        User user = this.select(query);
        if (null == user) return null;
        return convertShiroUser(user);
    }

    @Override
    public Set<String> getUserRoles(Long uid) {
        List<Role> roles = ssoDao.getRolesByUserId(uid);
        if (CollectionUtils.isNotEmpty(roles)) {
            return roles.stream().map(role -> {
                return role.getValue();
            }).collect(Collectors.toSet());
        }
        return null;
    }

    @Override
    public Set<String> getUserPermissions(Long uid) {
        List<Permission> permissions = this.listPermissions(uid);
        if (CollectionUtils.isNotEmpty(permissions)) {
            return permissions.stream().map((permission -> {
                return permission.getValue();
            })).collect(Collectors.toSet());
        }
        return null;
    }

    @Override
    public List<Permission> listPermissions(Long userId) {
        List<Permission> permissions = ssoDao.getPermissionsByUserId(userId);
        if (CollectionUtils.isNotEmpty(permissions)) return permissions;
        return null;
    }
}
