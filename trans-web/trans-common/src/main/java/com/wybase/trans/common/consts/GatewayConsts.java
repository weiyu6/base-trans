package com.wybase.trans.common.consts;

/**
 * 网关常量
 * @author weiyu
 * @date 2024/4/11
 */
public class GatewayConsts {
    private GatewayConsts(){

    }
    /**
     * 参数类型：1-predicate，2-filter
     */
    public static final String ROUTE_PARAM_TYPE_1 = "1";
    /**
     * 参数类型：1-predicate，2-filter
     */
    public static final String ROUTE_PARAM_TYPE_2 = "2";

    /**
     * 路由地址类型：1-lb模式(默认)，2-http模式
     */
    public static final String ROUTE_URI_TYPE_1 = "1";
    /**
     * 路由地址类型：1-lb模式(默认)，2-http模式
     */
    public static final String ROUTE_URI_TYPE_2 = "2";
}
