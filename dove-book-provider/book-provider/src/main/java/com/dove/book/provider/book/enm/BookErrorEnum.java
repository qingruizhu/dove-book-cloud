package com.dove.book.provider.book.enm;


import com.dove.common.core.enm.IBaseEum;

/**
 * @Description: 书籍业务异常 信息枚举
 * @Auther: qingruizhu
 * @Date: 2019-09-30 10:04
 */
public enum BookErrorEnum implements IBaseEum {

    BOOK_ERROR(2000, "操作【book】异常"),
    BOOK_ERROR_CREATE(2001, "创建【book】失败"),
    BOOK_ERROR_UPDATE(2001, "修改【book】失败"),
    BOOK_ERROR_DEL(2001, "删除【book】失败"),


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
