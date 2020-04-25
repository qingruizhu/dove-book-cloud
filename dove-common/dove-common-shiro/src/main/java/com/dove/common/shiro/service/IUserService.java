package com.dove.common.shiro.service;

import com.dove.common.shiro.dto.User;

import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/23 18:00
 */
public interface IUserService {

    /**
     * 根据名字获取用户
     *
     * @param username
     * @return
     */
    User getUserInfo(String username);

    User getUserInfo(Long uid);

    Set<String> getUserRoles(Long uid);

    Set<String> getUserPermissions(Long uid);
}
