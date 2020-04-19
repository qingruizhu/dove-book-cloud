package com.dove.book.exception;

import com.dove.book.enm.UserErrorEnum;
import com.dove.common.base.exception.BusinessException;

/**
 * @Description: 用户业务异常
 * @Author qingruizhu
 * @Date 10:58 上午 2020/4/12
 **/
public class UserBusinessException extends BusinessException {

    public UserBusinessException(String message) {
        super(message);
    }

    public UserBusinessException(UserErrorEnum enm) {
        super(enm);
    }
}
