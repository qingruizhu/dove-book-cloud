package com.dove.book.advice;

import com.dove.book.exception.BookBusinessException;
import com.dove.common.base.advice.GlobalExceptionHandle;
import com.dove.common.base.vo.CommonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description:
 * @Author qingruizhu
 * @Date 8:49 下午 2020/4/9
 **/
@RestControllerAdvice
public class BookExceptionAdvice extends GlobalExceptionHandle {

    @ExceptionHandler(BookBusinessException.class)
    public CommonResult handle(BookBusinessException e) {
        logger.error("---------【书籍业务】失败------>>>{}", e.getMessage());
        return CommonResult.failed(e.getCode(), e.getMessage());
    }

}
