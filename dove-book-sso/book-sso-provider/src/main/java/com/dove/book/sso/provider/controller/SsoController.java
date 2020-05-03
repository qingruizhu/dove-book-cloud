package com.dove.book.sso.provider.controller;

import com.dove.book.sso.provider.enm.SsoErrorEnum;
import com.dove.book.sso.provider.exception.SsoException;
import com.dove.book.sso.provider.service.ISsoService;
import com.dove.common.base.annotation.CommonResultAnnon;
import com.dove.common.base.vo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import static com.dove.common.shiro.core.filter.JwtAuthenFilter.AUTHORIZATION_HEADER;
import static com.dove.common.shiro.core.filter.JwtAuthenFilter.JWT_AUTHZSCHEME;


@Api(description = "登录/注册", tags = "☻ SSO")
@RestController
@RequestMapping("/sso")
public class SsoController {

    private Logger logger = LoggerFactory.getLogger(SsoController.class);

    @Resource
    private ISsoService service;


    //    @RequiresPermissions("query")
//    @RequiresRoles({"user"})

    @ApiOperation(value = "【用户名+密码】登录", consumes = "application/x-www-form-urlencoded")
    @PostMapping(value = "/login", consumes = "application/x-www-form-urlencoded")
    public CommonResult login(
            @ApiParam("用户名") @RequestParam("username") String username,
            @ApiParam("密码") @RequestParam("password") String password,
            HttpServletResponse response) {
        try {
            String token = service.login(username, password);
            response.setHeader(AUTHORIZATION_HEADER, JWT_AUTHZSCHEME + " " + token);
            return CommonResult.success();
        } catch (Exception e) {
            logger.error("【{}】登录失败", username);
            throw new SsoException(SsoErrorEnum.SSO_LOGIN_ERROR_USER);
        }
    }

    @ApiOperation(value = "【手机号+验证码】登录", consumes = "application/x-www-form-urlencoded")
    @PostMapping(value = "/loginByPhone", consumes = "application/x-www-form-urlencoded")
    public CommonResult loginByPhone(
            @ApiParam("手机号") @RequestParam("phone") String phone,
            @ApiParam("验证码") @RequestParam("authCode") String authCode,
            HttpServletResponse response) {
        try {
            String token = service.loginByPhone(phone, authCode);
            response.setHeader(AUTHORIZATION_HEADER, JWT_AUTHZSCHEME + " " + token);
            return CommonResult.success();
        } catch (Exception e) {
            logger.error("【手机号:{}】登录失败", phone);
            throw new SsoException(SsoErrorEnum.SSO_LOGIN_ERROR_PHONE);
        }

    }


    /**
     * pass:账号密码注册，随机码没有放入缓存，只在前端校验
     */
    @ApiOperation(value = "【用户名+密码】注册", consumes = "application/x-www-form-urlencoded")
    @PostMapping(value = "/regist", consumes = "application/x-www-form-urlencoded")
    @CommonResultAnnon
    public void regist(
            @ApiParam("用户名") @RequestParam("username") String username,
            @ApiParam("密码") @RequestParam("password") String password
            /*@RequestParam("authCode") String authCode*/) {
        service.regist(username, password);
    }


    @ApiOperation(value = "【手机号+验证码】注册", consumes = "application/x-www-form-urlencoded")
    @PostMapping(value = "/registByPhone", consumes = "application/x-www-form-urlencoded")
    @CommonResultAnnon
    public void registgByPhone(
            @ApiParam("手机号") @RequestParam("phone") String phone,
            @ApiParam("验证码 ") @RequestParam("authCode") String authCode) {
        service.registByPhone(phone, authCode);
    }

    @ApiOperation("退出登录")
    @GetMapping(value = "/logout")
    @CommonResultAnnon
    public void logout() {
        service.logout();
    }

}
