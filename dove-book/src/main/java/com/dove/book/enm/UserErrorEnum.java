package com.dove.book.enm;


import com.dove.common.base.enm.IBaseEum;

/**
 * @Description: 用户业务异常 信息枚举
 * @Auther: qingruizhu
 * @Date: 2019-09-30 10:04
 */
public enum UserErrorEnum implements IBaseEum {

    BOOK_ERROR(1000, "操作【User】异常"),
    BOOK_ERROR_CREATE(1001, "创建【User】失败"),

    ;


    private int code;
    private String message;

    UserErrorEnum(int code, String message) {
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
