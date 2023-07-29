package com.wybase.trans.base.result;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局统一返回结果类
 * @author weiyu
 * @date 2023/7/29
 */
@Data
@NoArgsConstructor
public class Result {
    // 响应码
    private String code;
    // 响应信息
    private String msg;
    // 响应数据
    private Map<String, Object> data = new HashMap<>();

    /**
     * 返回成功结果
     * @return
     */
    public static Result ok() {
        Result r = new Result();
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMsg(ResultCodeEnum.SUCCESS.getMsg());
        return r;
    }

    /**
     * 返回失败结果
     * @return
     */
    public static Result fail() {
        Result r = new Result();
        r.setCode(ResultCodeEnum.FAIL.getCode());
        r.setMsg(ResultCodeEnum.FAIL.getMsg());
        return r;
    }

    /**
     * 设置特定的结果
     * @param resultCodeEnum
     * @return
     */
    public static Result fail(ResultCodeEnum resultCodeEnum) {
        Result r = new Result();
        r.setCode(resultCodeEnum.getCode());
        r.setMsg(resultCodeEnum.getMsg());
        return r;
    }

    public static Result build(String code, String msg) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

    /**
     * 设置特定的响应消息
     * @param message
     * @return
     */
    public Result msg(String message) {
        this.setMsg(message);
        return this;
    }

    /**
     * 设置特定的响应码
     * @param code
     * @return
     */
    public Result code(String code) {
        this.setCode(code);
        return this;
    }
}
