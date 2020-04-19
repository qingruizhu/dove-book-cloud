package com.dove.common.base.advice;


import com.dove.common.base.enm.SysErrorEnum;
import com.dove.common.base.exception.BusinessException;
import com.dove.common.base.vo.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;

//@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandle {
    protected Logger logger = LoggerFactory.getLogger(GlobalExceptionHandle.class);

    @ExceptionHandler(Exception.class)
    public CommonResult handle(Exception e) {
        logger.error(SysErrorEnum.SYSTEM_ERROR.getMessage(), e);
        return CommonResult.failed();
    }

    @ExceptionHandler(BusinessException.class)
    public CommonResult handle(BusinessException e) {
        logger.error("---------【基础业务】失败------>>>{}", e.getMessage());
        return CommonResult.failed(e.getCode(), e.getMessage());
    }


    //请求方式有误
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResult handle(HttpRequestMethodNotSupportedException e) {
        StringBuilder sb = new StringBuilder(SysErrorEnum.SYSTEM_ERROR_REQUEST_METHOD.getMessage());
        sb.append("【当前请求方式=");
        sb.append(e.getMethod());
        sb.append(",支持请求方式=");
        Arrays.stream(e.getSupportedMethods()).forEach((mth) -> {
            sb.append(mth + " ");
        });
        sb.append("】");
        return CommonResult.failed(SysErrorEnum.SYSTEM_ERROR_REQUEST_METHOD.getCode(), sb.toString());
    }

    //参数丢失
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public CommonResult handle(MissingServletRequestParameterException e) {
        String parameterName = e.getParameterName();
        return CommonResult.failed(SysErrorEnum.SYSTEM_ERROR_REQUEST_PARAM_MISSING.getCode(), SysErrorEnum.SYSTEM_ERROR_REQUEST_PARAM_MISSING.getMessage() + "【" + parameterName + "】");
    }

    //参数较验不通过
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult handle(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        for (ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                return CommonResult.failed(SysErrorEnum.SYSTEM_ERROR_REQUEST_PARAM_INVALIDATE.getCode(), fieldError.getDefaultMessage());
            }
        }
        return CommonResult.failed();
    }

    //参数较验不通过
    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResult handle(ConstraintViolationException e) {
        for (ConstraintViolation<?> s : e.getConstraintViolations()) {
            return CommonResult.failed(SysErrorEnum.SYSTEM_ERROR_REQUEST_PARAM_INVALIDATE.getCode(), s.getInvalidValue() + " ❎<--- " + s.getMessage());
        }
        return CommonResult.failed();
    }


}