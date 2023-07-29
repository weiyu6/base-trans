package com.wybase.trans.base.exception;

import com.wybase.trans.base.result.ResultCodeEnum;
import lombok.Data;
import lombok.ToString;

/**
 * 交易系统自定义全局统一异常类
 * @author weiyu
 * @date 2023/07/29
 */
@Data
@ToString
public class TransException extends RuntimeException {
    // 错误码
    private final String errorCode;
    // 错误信息
    private final String errorMsg;

    public TransException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public TransException(String errorCode, String errorMsg, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    /**
     * @param resultCodeEnum 异常枚举
     */
    public TransException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMsg());
        this.errorCode = resultCodeEnum.getCode();
        this.errorMsg = resultCodeEnum.getMsg();
    }

    /**
     * @param resultCodeEnum 枚举异常
     * @param resmsg 自定义错误信息
     */
    public TransException(ResultCodeEnum resultCodeEnum, String resmsg) {
        super(resmsg);
        this.errorCode = resultCodeEnum.getCode();
        this.errorMsg = resmsg;
    }

    /**
     * @param resultCodeEnum 异常枚举
     * @param cause 原始异常对象
     */
    public TransException(ResultCodeEnum resultCodeEnum, Throwable cause) {
        super(cause);
        this.errorCode = resultCodeEnum.getCode();
        this.errorMsg = resultCodeEnum.getMsg();
    }
}
