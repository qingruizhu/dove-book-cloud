package com.dove.book.enm;


import com.dove.common.base.enm.IBaseEum;

/**
 * @Description: 书籍业务异常 信息枚举
 * @Auther: qingruizhu
 * @Date: 2019-09-30 10:04
 */
public enum BookErrorEnum implements IBaseEum {

    AUTHORITY_ERROR(1000, "权限不清"),
    AUTHORITY_INFO_ERROR_UPDATE(1001, "权限信息错误"),
    AUTHORITY_ACCOUNT_ERROR_BALANCE(1301, "权限数量错误"),

    ;


    private int code;
    private String message;

    BookErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
