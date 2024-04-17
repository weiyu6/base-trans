package com.wybase.trans.gateway.model.entity.custom;

import lombok.Data;
import lombok.ToString;

import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author weiyu
 * @date 2024/4/16
 */
@Data
@ToString
public class RouteExtend {
    private BigInteger id;

    /**
     * 路由id
     */
    private String routeId;

    /**
     * 路由地址
     */
    private String uri;

    /**
     * 路由地址类型：1-lb模式(默认)，2-http模式
     */
    private String uriType;

    /**
     * 排序字段，越小越靠前
     */
    private Integer sort;

    /**
     * 路由简介
     */
    private String content;

    /**
     * 路由状态：0-启用，1-禁用
     */
    private String routeStat;

    /**
     * 路由参数
     */
    private List<RouteParamExtend> routeParams;

}
