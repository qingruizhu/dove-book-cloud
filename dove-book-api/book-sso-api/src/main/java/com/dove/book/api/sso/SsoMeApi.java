package com.dove.book.api.sso;

import com.dove.book.api.sso.dto.UserDto;
import com.dove.book.api.sso.hystrix.SsoMeHystrix;
import com.dove.book.api.sso.vo.UserVo;
import com.dove.common.base.annotation.CommonResultAnnon;
import com.dove.common.base.validate.QueryGroup;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "book-sso-provider", fallback = SsoMeHystrix.class)
@CommonResultAnnon
public interface SsoMeApi {

    @GetMapping(value = "/me/info")
    public UserVo info();

    @PostMapping("/me/updatePhone")
    public void updatePhone(@RequestParam String phone, @RequestParam String authCode);

    @PostMapping("/me/updatePassword")
    public void updatePassword(@RequestParam String originalPwd, @RequestParam String targetPwd, @RequestParam String authCode);

    @PostMapping("/me/initPassword")
    public void initPassword(@RequestParam String password, @RequestParam String authCode);

    @PostMapping("/me/updateInfo")
    public void updateInfo(
            @RequestBody @Validated(QueryGroup.Update.class) UserDto user);


}
