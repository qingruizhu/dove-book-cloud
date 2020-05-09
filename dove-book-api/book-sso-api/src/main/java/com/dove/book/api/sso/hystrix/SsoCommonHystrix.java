package com.dove.book.api.sso.hystrix;

import com.dove.book.api.sso.SsoCommonApi;
import com.dove.book.api.sso.vo.UserVo;
import com.dove.common.base.vo.CommonResult;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/5/8 11:41
 */
@Component
public class SsoCommonHystrix implements SsoCommonApi {
    @Override
    public CommonResult getUser() {
        return null;
    }
    @Override
    public UserVo getUser1() {
        return null;
    }
    @Override
    public CommonResult getAuthCode(String randomPrefix, Integer randomLen, String randomType) {
        return null;
    }

    @Override
    public void checkAuthCode(String authPrefix, String authCode) {
        return;
    }
}
