package com.wybase.trans.base.exception;

import com.wybase.trans.base.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 交易系统异常拦截
 * @author weiyu
 * @date 2023/07/29
 */
@RestControllerAdvice
public class TransExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(TransExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public Result handException(Exception e) {
        logger.error(e.getMessage(), e);
        return Result.fail();
    }

    /**
     * 系统自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(TransException.class)
    public Result businessException(TransException e) {
        logger.error(e.getErrorMsg(), e);
        return Result.build(e.getErrorCode(), e.getErrorMsg());
    }
}
