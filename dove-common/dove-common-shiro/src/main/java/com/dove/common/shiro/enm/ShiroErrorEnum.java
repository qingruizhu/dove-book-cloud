package com.dove.common.shiro.enm;

import com.dove.common.base.enm.IBaseEum;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/23 13:25
 */
public enum ShiroErrorEnum implements IBaseEum {

    AUTHEN_ERROR(1, "身份认证失败"),
    AUTHEN_ERROR_TOKEN(1, "认证身份失败,请不要篡改[token]"),
    AUTHOR_ERROR_PERMISSION(2, "没有操作权限"),
    AUTHOR_ERROR_ROLE(2, "没有操作角色"),
    JWT_ERROR_SIGN(300, "jwt签名失败"),
    JWT_ERROR_SIGN_HVT_KEY_UID(500, "jwt签名失败,[claims]未设置[uid]"),
    JWT_ERROR_GET_ALGORITHM(500, "jwt异常,获取[Algorithm]失败"),
    SYSTEM_ERROR_REQUEST_PARAM_INVALIDATE(501, "非法参数"),
    SYSTEM_ERROR_REQUEST_PARAM_MISSING(502, "必输参数缺失"),
    SYSTEM_ERROR_REQUEST_METHOD(503, "请求方式有误"),
    _ERROR(777, "无码错误");;


    private int code;
    private String message;

    ShiroErrorEnum(int code, String message) {
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
