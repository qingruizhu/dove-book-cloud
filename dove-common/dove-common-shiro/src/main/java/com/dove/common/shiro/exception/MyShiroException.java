package com.dove.common.shiro.exception;


import com.dove.common.base.exception.BaseException;
import com.dove.common.shiro.enm.ShiroErrorEnum;

/**
 * @Description: shiro异常
 * @Auther: qingruizhu
 * @Date: 2020/4/23 19:54
 */
public class MyShiroException extends BaseException {
    public MyShiroException(String message) {
        super(message);
    }

    public MyShiroException(ShiroErrorEnum enm) {
        super(enm.getCode(), enm.getMessage());
    }
}
