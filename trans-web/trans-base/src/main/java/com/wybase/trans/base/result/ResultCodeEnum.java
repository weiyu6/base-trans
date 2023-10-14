package com.wybase.trans.base.result;

import lombok.Getter;

/**
 * 响应信息枚举类
 * @author weiyu
 * @date 2023/07/29
 */
@Getter
public enum ResultCodeEnum {
    SUCCESS("000000", "处理成功"),
    FAIL("100000", "处理失败"),
    ERROR("888888", "系统错误"),

    // 2xx 参数校验
    /**
     * 200000-认证失败
     */
    NO_AUTH("200000", "认证失败"),
    NO_ACCREDIT("300000", "授权失败"),

    /**
     * 不能为空
     */
    NULL_ERROR("100201", ""),
    /**
     * 100208-用户不存在
     */
    LOGIN_USERNM_ERROR("100202", "用户不存在"),
    /**
     * 100209-密码错误
     */
    LOGIN_PASSWORD_ERROR("100203", "密码错误"),

    /**
     * 100701-查询无记录
     */
    TRAN100701("100701", "查询无记录"),
    /**
     * 100704-用户名已注册
     */
    REGISTERED_USERNM_ERROR("100704", "用户名已注册"),
    /**
     * 100706-记录信息已删除
     */
    RECDSTAT_ERROR("100706", "记录信息已删除"),
    ;

    // 响应码
    private String code;
    // 响应信息
    private String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
