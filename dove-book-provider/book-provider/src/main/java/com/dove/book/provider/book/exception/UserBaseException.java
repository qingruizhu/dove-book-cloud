package com.dove.book.provider.book.exception;

import com.dove.book.provider.book.enm.UserErrorEnum;
import com.dove.common.base.exception.BaseException;

/**
 * @Description: 用户业务异常
 * @Author qingruizhu
 * @Date 10:58 上午 2020/4/12
 **/
public class UserBaseException extends BaseException {

    public UserBaseException(String message) {
        super(message);
    }

    public UserBaseException(UserErrorEnum enm) {
        super(enm);
    }
}
