package com.dove.book.api.sso.hystrix;

import com.dove.book.api.sso.SsoMeApi;
import com.dove.book.api.sso.dto.UserDto;
import com.dove.book.api.sso.vo.UserVo;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/5/8 11:41
 */
@Component
public class SsoMeHystrix implements SsoMeApi {

    @Override
    public UserVo info() {
        return null;
    }

    @Override
    public void updatePhone(String phone, String authCode) {

    }

    @Override
    public void updatePassword(String originalPwd, String targetPwd, String authCode) {

    }

    @Override
    public void initPassword(String password, String authCode) {

    }

    @Override
    public void updateInfo(UserDto user) {

    }
}
