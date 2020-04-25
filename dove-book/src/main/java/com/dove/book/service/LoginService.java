package com.dove.book.service;

import com.dove.book.bgd.model.User;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/20 23:12
 */
public interface LoginService {
    User getUserByName(String username);
}
