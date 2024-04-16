package com.wybase.trans.gateway.model.vo;

import com.wybase.trans.gateway.model.entity.generate.GatewayRouteParam;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author weiyu
 * @date 2024/4/16
 */
@Data
@ToString
public class RouteVo {
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
     * 路由参数
     */
    private List<GatewayRouteParam> routeParams;

    /**
     *页码
     */
    private int pageNum = 1;

    /**
     * 每页显示数量
     */
    private int pageSize = 10;
}
