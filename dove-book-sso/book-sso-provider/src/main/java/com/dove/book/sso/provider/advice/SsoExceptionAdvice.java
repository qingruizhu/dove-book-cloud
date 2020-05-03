package com.dove.book.sso.provider.advice;

import com.dove.book.sso.provider.exception.SsoException;
import com.dove.common.base.vo.CommonResult;
import com.dove.common.shiro.core.advice.ShiroExceptionAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description:
 * @Author qingruizhu
 * @Date 1:49 下午 2020/4/27
 **/
@RestControllerAdvice
public final class SsoExceptionAdvice {

    @ExceptionHandler(SsoException.class)
    public CommonResult handle(SsoException e) {
//        logger.error("【SSO】失败-    -->>>{}", e.getMessage());
        return CommonResult.failed(e.getCode(), e.getMessage());
    }

}
