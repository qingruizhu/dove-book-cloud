package com.dove.common.base.exception;

import com.dove.common.base.enm.IBaseEum;

/**
 * @Description: 业务异常
 * @Auther: qingruizhu
 * @Date: 2020/4/9 19:54
 */
public class BusinessException extends BaseException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(IBaseEum enm) {
        super(enm.getCode(), enm.getMessage());
    }
}
