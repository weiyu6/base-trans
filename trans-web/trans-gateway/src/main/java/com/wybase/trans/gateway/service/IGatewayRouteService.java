package com.wybase.trans.gateway.service;

import com.mybatisflex.core.service.IService;
import com.wybase.trans.gateway.model.entity.generate.GatewayRoute;

import java.util.List;

/**
 * 网关路由信息表 服务层。
 *
 * @author weiyu
 * @since 2024-04-11
 */
public interface IGatewayRouteService extends IService<GatewayRoute> {

    /**
     * 查询网关路由信息表列表。
     */
    List<GatewayRoute> getRouteList();
}
