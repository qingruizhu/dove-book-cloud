package com.dove.sso.advice;

import com.dove.common.base.vo.CommonResult;
import com.dove.common.shiro.advice.ShiroExceptionAdvice;
import com.dove.sso.exception.SsoException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description:
 * @Author qingruizhu
 * @Date 1:49 下午 2020/4/27
 **/
@RestControllerAdvice
public final class SsoExceptionAdvice extends ShiroExceptionAdvice {

    @ExceptionHandler(SsoException.class)
    public CommonResult handle(SsoException e) {
        logger.error("---------【SSO】失败------>>>", e);
        return CommonResult.failed(e.getCode(), e.getMessage());
    }

}
