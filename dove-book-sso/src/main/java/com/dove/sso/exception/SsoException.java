package com.dove.sso.exception;


import com.dove.common.core.exception.DoveException;
import com.dove.sso.enm.SsoErrorEnum;

/**
 * @Description: shiro异常
 * @Auther: qingruizhu
 * @Date: 2020/4/23 19:54
 */
public class SsoException extends DoveException {
    public SsoException(String message) {
        super(message);
    }

    public SsoException(SsoErrorEnum enm) {
        super(enm.getCode(), enm.getMessage());
    }
}
