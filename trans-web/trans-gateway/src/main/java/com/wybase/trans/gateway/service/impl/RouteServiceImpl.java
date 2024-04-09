package com.wybase.trans.gateway.service.impl;

import com.wybase.trans.gateway.service.RouteService;
import jakarta.annotation.Resource;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 路由规则服务实现类
 * @author weiyu
 * @date 2024/4/9
 */
@Service
public class RouteServiceImpl implements RouteService {

    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;
    @Resource
    private RouteDefinitionLocator routeDefinitionLocator;

    /**
     * 获取所有路由规则
     * @return 包含所有路由规则的 Flux 对象
     */
    @Override
    public Flux<RouteDefinition> getAllRoutes() {
        return routeDefinitionLocator.getRouteDefinitions();
    }

    /**
     * 根据路由 ID 删除指定路由规则
     * @param routeId 要删除的路由规则的唯一标识符
     * @return 删除操作完成后的 Mono 对象
     */
    @Override
    public Mono<Void> deleteRouteById(String routeId) {
        return routeDefinitionWriter.delete(Mono.just(routeId));
    }

    /**
     * 刷新所有路由规则，使得新的路由规则立即生效
     * @return 刷新操作完成后的 Mono 对象
     */
    @Override
    public Mono<Void> refreshAllRoutes() {
        return routeDefinitionLocator.getRouteDefinitions()
                .flatMap(routeDefinition -> routeDefinitionWriter.save(Mono.just(routeDefinition)))
                .then();
    }

    /**
     * 根据路由 ID 刷新指定路由规则，使得新的路由规则立即生效
     * @param routeId 要刷新的路由规则的唯一标识符
     * @return 刷新操作完成后的 Mono 对象
     */
    @Override
    public Mono<Void> refreshRouteById(String routeId) {
        return routeDefinitionLocator.getRouteDefinitions()// 获取所有路由定义
                .filter(routeDefinition -> routeDefinition.getId().equals(routeId))// 筛选出与指定routeId匹配的路由定义
                .flatMap(routeDefinition -> routeDefinitionWriter.save(Mono.just(routeDefinition)))// 为每个匹配的路由定义调用保存操作
                .then();// 等待所有保存操作完成
    }

    /**
     * 添加新的路由规则
     * @param routeDefinition 要添加的路由规则对象
     * @return 添加操作完成后的 Mono 对象，包含添加后的路由规则信息
     */
    @Override
    public Mono<ResponseEntity<?>> addRoute(RouteDefinition routeDefinition) {
        // 将路由定义保存到routeDefinitionWriter，并订阅其完成事件
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        return Mono.just(ResponseEntity.ok().build());
    }
}
