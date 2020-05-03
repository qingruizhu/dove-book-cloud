package com.dove.book.advice;

import com.dove.book.exception.BookBaseException;
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

    @ExceptionHandler(BookBaseException.class)
    public CommonResult handle(BookBaseException e) {
        logger.error(snmAppendErrorMsg("【书籍业务】失败------>>>{}"), e.getMessage());
        return CommonResult.failed(e.getCode(), e.getMessage());
    }

}
