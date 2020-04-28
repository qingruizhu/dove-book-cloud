package com.dove.common.shiro.enm;

import com.dove.common.core.enm.IBaseEum;

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
    ;
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
