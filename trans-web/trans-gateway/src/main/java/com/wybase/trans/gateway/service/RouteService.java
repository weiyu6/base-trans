package com.wybase.trans.gateway.service;

import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 路由管理服务接口，用于管理动态路由规则
 * @author weiyu
 * @date 2024/4/9
 */
public interface RouteService {

    /**
     * 获取所有路由规则
     * @return 包含所有路由规则的 Flux 对象
     */
    public Flux<RouteDefinition> getAllRoutes();

    /**
     * 根据路由 ID 删除指定路由规则
     * @param routeId 要删除的路由规则的唯一标识符
     * @return 删除操作完成后的 Mono 对象
     */
    public Mono<Void> deleteRouteById(String routeId);

    /**
     * 刷新所有路由规则，使得新的路由规则立即生效
     * @return 刷新操作完成后的 Mono 对象
     */
    public Mono<Void> refreshAllRoutes();

    /**
     * 根据路由 ID 刷新指定路由规则，使得新的路由规则立即生效
     * @param routeId 要刷新的路由规则的唯一标识符
     * @return 刷新操作完成后的 Mono 对象
     */
    public Mono<Void> refreshRouteById(String routeId);

    /**
     * 添加新的路由规则
     * @param routeDefinition 要添加的路由规则对象
     * @return 添加操作完成后的 Mono 对象，包含添加后的路由规则信息
     */
    public Mono<ResponseEntity<?>> addRoute(RouteDefinition routeDefinition);

}
