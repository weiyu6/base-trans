package com.wybase.trans.gateway.service;

import com.mybatisflex.core.service.IService;
import com.wybase.trans.gateway.model.dto.RouteInput;
import com.wybase.trans.gateway.model.dto.RouteOutput;
import com.wybase.trans.gateway.model.entity.generate.GatewayRoute;

import java.util.List;

/**
 * 网关路由信息表 服务层。
 * @author weiyu
 * @since 2024-04-11
 */
public interface IGatewayRouteService extends IService<GatewayRoute> {

    /**
     * 查询网关路由信息表列表。
     */
    List<GatewayRoute> getRouteList();

    /**
     * 获取所有路由规则
     */
    public RouteOutput getAllRoutes(RouteInput input);

    /**
     * 根据路由 ID 删除指定路由规则
     * @param routeId 要删除的路由规则的唯一标识符
     */
    void deleteRouteById(String routeId);

    /**
     * 刷新所有路由规则，使得新的路由规则立即生效
     */
    void refreshAllRoutes();

    /**
     * 根据路由 ID 刷新指定路由规则，使得新的路由规则立即生效
     * @param routeId 要刷新的路由规则的唯一标识符
     */
    void refreshRouteById(String routeId);

    /**
     * 添加新的路由规则
     */
    void addRoute(RouteInput routeInput);
}
