package com.dove.book.provider.sso.enm;

import com.dove.common.core.enm.IBaseEum;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/23 13:25
 */
public enum SsoErrorEnum implements IBaseEum {

    SSO_ERROR(1, "【SSO】异常"),
    //公共
    SSO_COMMON_ERROR_AUTHCODE_GENERATE(1, "生成验证码失败"),
    SSO_COMMON_ERROR_AUTHCODE_INVALID(1, "验证码失效"),
    SSO_COMMON_ERROR_AUTHCODE_WRONG(1, "验证码输入错误"),
    //登录
    SSO_LOGIN_ERROR_USER(1, "登录失败,用户名或密码错误"),
    SSO_LOGIN_ERROR_PHONE(1, "登录失败,手机号错误"),
    //注册
    SSO_REGIST_ERROR(1, "注册失败！"),
    SSO_REGIST_ERROR_USER_EXIST(1, "注册失败,用户名已经存在"),
    SSO_REGIST_ERROR_PHONE_EXIST(1, "注册失败,手机号已经存在"),
    //个人信息
    SSO_ME_ERROR_INFO_UPDATE(1, "修改个人信息失败!"),
    SSO_ME_ERROR_PHONE_UPDATE(1, "手机号设置/修改失败!"),
    SSO_ME_ERROR_PWD_UPDATE(1, "修改密码失败!"),
    SSO_ME_ERROR_PWD_UPDATE_OLD(1, "修改密码失败,旧密码输入错误!"),
    SSO_ME_ERROR_PWD_UPDATE_HVT_PHONE(1, "修改密码失败,手机号不存在!"),
    SSO_ME_ERROR_PWD_INIT(1, "初始化密码失败!"),
    SSO_ME_ERROR_PWD_INIT_EXIST(1, "初始化密码失败,密码已存在!"),


    ;
    private int code;
    private String message;

    SsoErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
