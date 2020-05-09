package com.dove.book.api.sso;

import com.dove.book.api.sso.vo.UserVo;
import com.dove.book.api.sso.hystrix.SsoCommonHystrix;
import com.dove.common.base.annotation.CommonResultAnnon;
import com.dove.common.base.vo.CommonResult;
import com.dove.common.util.random.RandomUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "book-sso-provider", fallback = SsoCommonHystrix.class)
public interface SsoCommonApi {


    @GetMapping(value = "/common/getUser")
    @CommonResultAnnon
    CommonResult<UserVo> getUser();

    @GetMapping(value = "/common/getUser1")
    @CommonResultAnnon
    UserVo getUser1();

    @GetMapping(value = "/common/getAuthCode")
    CommonResult<String> getAuthCode(
            @RequestParam(required = false) String randomPrefix,
            @RequestParam(defaultValue = "4") Integer randomLen,
            @RequestParam(defaultValue = RandomUtil.RANDOM_TYPE_NUM) String randomType);

    @PostMapping(value = "/common/checkAuthCode")
    @CommonResultAnnon
    void checkAuthCode(
            @RequestParam String authPrefix,
            @RequestParam String authCode);

}
