package com.wybase.trans.gateway.service;

import com.mybatisflex.core.service.IService;
import com.wybase.trans.gateway.model.entity.generate.GatewayRouteParam;

import java.util.List;

/**
 * 网关路由参数表 服务层。
 * @author weiyu
 * @since 2024-04-11
 */
public interface IGatewayRouteParamService extends IService<GatewayRouteParam> {

    /**
     * 根据路由ID查询路由参数
     */
    List<GatewayRouteParam> routeParamByRouteId(String routeId);
}
