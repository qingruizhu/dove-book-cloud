package com.dove.book.exception;

import com.dove.book.enm.BookErrorEnum;
import com.dove.common.base.exception.BaseException;

/**
 * @Description: 书籍业务异常
 * @Author qingruizhu
 * @Date 10:58 上午 2020/4/12
 **/
public class BookBaseException extends BaseException {

    public BookBaseException(String message) {
        super(message);
    }

    public BookBaseException(BookErrorEnum enm) {
        super(enm);
    }
}
