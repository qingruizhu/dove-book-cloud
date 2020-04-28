package com.dove.sso.service;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/27 11:47
 */
public interface SsoService {

    String login(String name, String password);

    String loginByPhone(String phone, String authCode);
}
