package com.dove.book.provider.sso.exception;


import com.dove.book.provider.sso.enm.SsoErrorEnum;
import com.dove.common.base.exception.BaseException;

/**
 * @Description: shiro异常
 * @Auther: qingruizhu
 * @Date: 2020/4/23 19:54
 */
public class SsoException extends BaseException {
    public SsoException(String message) {
        super(message);
    }

    public SsoException(SsoErrorEnum enm) {
        super(enm);
    }
}
