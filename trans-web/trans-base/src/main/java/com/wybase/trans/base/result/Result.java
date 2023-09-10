package com.wybase.trans.base.result;

import lombok.Data;

/**
 * 全局统一返回结果类
 * @author weiyu
 * @date 2023/7/29
 */
@Data
public class Result<T> {
    // 响应码
    private String code;
    // 响应信息
    private String msg;
    // 返回数据
    private T data;

    protected static <T> Result<T> build(T data) {
        Result<T> result = new Result<>();
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMsg(resultCodeEnum.getMsg());
        return result;
    }

    public static <T> Result<T> build(String code, String message) {
        Result<T> result = build(null);
        result.setCode(code);
        result.setMsg(message);
        return result;
    }

    public static <T> Result<T> ok() {
        return Result.ok(null);
    }

    /**
     * 操作成功
     */
    public static <T> Result<T> ok(T data) {

        return build(data, ResultCodeEnum.SUCCESS);
    }

    public static <T> Result<T> fail() {
        return Result.fail(null);
    }

    public static <T> Result<T> error() {
        return build(null, ResultCodeEnum.ERROR);
    }

    /**
     * 操作失败
     */
    public static <T> Result<T> fail(T data) {

        return build(data, ResultCodeEnum.FAIL);
    }

    public Result<T> message(String msg) {
        this.setMsg(msg);
        return this;
    }

    public Result<T> code(String code) {
        this.setCode(code);
        return this;
    }

    public Result<T> errorMsg(String code, String msg) {
        return build(code, msg);
    }
}
