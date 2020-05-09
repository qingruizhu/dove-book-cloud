package com.dove.book.provider.sso.service;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @Description: 公共业务
 * @Auther: qingruizhu
 * @Date: 2020/4/30 09:28
 */
@Validated
public interface ICommonService {

    boolean cacheAuthCode(@NotBlank(message = "缓存前缀不能为空") String randomPrefix, @NotBlank(message = "缓存的验证码不能为空") String authCode);

    void checkAuthCode(@NotBlank(message = "校验的手机号/用户名不能为空") String authPrefix, @NotBlank(message = "校验的验证码不能为空") String authCode);
}
