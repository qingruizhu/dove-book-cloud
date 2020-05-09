package com.dove.book.provider.sso.controller;

import com.dove.book.api.sso.SsoCommonApi;
import com.dove.book.api.sso.vo.UserVo;
import com.dove.book.provider.sso.enm.SsoErrorEnum;
import com.dove.book.provider.sso.service.ICommonService;
import com.dove.common.base.annotation.CommonResultAnnon;
import com.dove.common.base.vo.CommonResult;
import com.dove.common.util.random.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description: 公共
 * @Auther: qingruizhu
 * @Date: 2019-12-05 16:19
 */
@Api(description = "公共接口", tags = "☻ Common")
@RestController
public class CommonController implements SsoCommonApi {

    @Autowired
    private ICommonService service;

    //    @Override
    @CommonResultAnnon
    public CommonResult getUser() {
        UserVo userVo = new UserVo();
        userVo.setId((long) 3);
        userVo.setUsername("祝清瑞");
        return CommonResult.success(userVo);
    }

    @CommonResultAnnon
    public UserVo getUser1() {
        UserVo userVo = new UserVo();
        userVo.setId((long) 3);
        userVo.setUsername("祝清瑞1");
        return userVo;
    }

    @ApiOperation("获取【验证码】")
    public CommonResult<String> getAuthCode(
            @ApiParam("手机号或用户名") String randomPrefix,
            @ApiParam("验证码长度") Integer randomLen,
            @ApiParam("验证码组成类型") String randomType) {
        String randomCode = RandomUtil.generateRandom(randomLen, randomType);
        if (StringUtils.isBlank(randomPrefix))
            return CommonResult.success(randomCode, "获取随机码成功");
        if (service.cacheAuthCode(randomPrefix, randomCode))
            return CommonResult.success(randomCode, "获取随机码成功");
        return CommonResult.failed(SsoErrorEnum.SSO_COMMON_ERROR_AUTHCODE_GENERATE);
    }

    @ApiOperation("校验【验证码】")
    public void checkAuthCode(
            @ApiParam("手机号或用户名") String authPrefix,
            @ApiParam("验证码") String authCode) {
        service.checkAuthCode(authPrefix, authCode);
    }


}
