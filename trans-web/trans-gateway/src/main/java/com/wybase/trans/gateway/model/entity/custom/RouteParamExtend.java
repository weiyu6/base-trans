package com.wybase.trans.gateway.model.entity.custom;

import lombok.Data;
import lombok.ToString;

import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 *
 * @author weiyu
 * @date 2024/4/16
 */
@Data
@ToString
public class RouteParamExtend {
    private BigInteger id;

    /**
     * 路由id
     */
    private String routeId;

    /**
     * 参数value
     */
    private String paramValue;

    /**
     * 参数类型：1-predicate，2-filter
     */
    private String paramType;

    /**
     * 路由参数简介
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
