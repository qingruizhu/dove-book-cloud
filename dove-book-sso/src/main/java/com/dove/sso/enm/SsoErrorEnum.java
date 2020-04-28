package com.dove.sso.enm;

import com.dove.common.core.enm.IBaseEum;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/23 13:25
 */
public enum SsoErrorEnum implements IBaseEum {

    SSO_ERROR(1, "【SSO】异常"),
    SSO_ERROR_AUTHCODE(1, "验证码有误"),
    SSO_LOGIN_ERROR_USER(1, "登录失败,用户名或密码错误"),
    SSO_LOGIN_ERROR_PHONE(1, "登录失败,手机号错误"),
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
