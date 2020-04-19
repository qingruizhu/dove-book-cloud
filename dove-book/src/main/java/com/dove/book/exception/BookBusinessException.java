package com.dove.book.exception;

import com.dove.book.enm.BookErrorEnum;
import com.dove.common.base.exception.BusinessException;

/**
 * @Description: 书籍业务异常
 * @Author qingruizhu
 * @Date 10:58 上午 2020/4/12
 **/
public class BookBusinessException extends BusinessException {

    public BookBusinessException(String message) {
        super(message);
    }

    public BookBusinessException(BookErrorEnum enm) {
        super(enm);
    }
}
