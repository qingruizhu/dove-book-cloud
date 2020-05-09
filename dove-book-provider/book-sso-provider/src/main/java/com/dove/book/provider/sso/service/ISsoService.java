package com.dove.book.provider.sso.service;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/27 11:47
 */
@Validated
public interface ISsoService {

    String login(@NotBlank String username, @NotBlank String password);

    String loginByPhone(@NotBlank String phone, @NotBlank String authCode);

    void regist(@NotBlank String username, @NotBlank String password);

    void registByPhone(@NotBlank String phone, @NotBlank String authCode);

    void logout();

}
