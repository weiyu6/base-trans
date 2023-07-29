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
