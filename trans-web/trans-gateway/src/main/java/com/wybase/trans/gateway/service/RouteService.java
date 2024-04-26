package com.wybase.trans.gateway.service;

import com.wybase.trans.gateway.model.dto.RouteInput;
import com.wybase.trans.gateway.model.dto.RouteOutput;

/**
 * 路由管理服务接口，用于管理动态路由规则
 * @author weiyu
 * @date 2024/4/9
 */
public interface RouteService {

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

    /**
     * 更新路由规则
     */
    void updateRoute(RouteInput input);

    /**
     * 根据路由 ID 获取路由规则
     */
    RouteOutput getRouteById(String routeId);
}
