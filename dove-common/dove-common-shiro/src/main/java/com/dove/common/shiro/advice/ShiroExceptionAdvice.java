package com.dove.common.shiro.advice;

import com.dove.common.base.advice.GlobalExceptionHandle;
import com.dove.common.base.vo.CommonResult;
import com.dove.common.shiro.exception.MyShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.dove.common.shiro.enm.ShiroErrorEnum.AUTHEN_ERROR;

/**
 * @Description:
 * @Author qingruizhu
 * @Date 8:49 下午 2020/4/9
 **/
@RestControllerAdvice
public class ShiroExceptionAdvice extends GlobalExceptionHandle {


    @ExceptionHandler(AuthenticationException.class)
    public CommonResult handle(AuthenticationException e) {
        logger.error("---------【shiro:身份认证】失败------>>>", e);
        return CommonResult.failed(AUTHEN_ERROR);
    }

    @ExceptionHandler(MyShiroException.class)
    public CommonResult handle(MyShiroException e) {
        logger.error("---------【shiro】失败------>>>{}", e.getMessage());
        return CommonResult.failed(e.getCode(), e.getMessage());
    }


}
