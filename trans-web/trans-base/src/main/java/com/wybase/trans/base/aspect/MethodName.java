package com.wybase.trans.base.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法名注解
 * 添加此注解的方法，请求时会进行拦截，记录相关日志信息
 * @author weiyu
 * @date 2023/7/29
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodName {

    /**
     * 业务名称
     */
    String value() default "";

    /**
     * 是否记录日志
     */
    boolean save() default true;

    /**
     * 交易类型
     */
    String transType() default "";
}
