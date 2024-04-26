package com.wybase.trans.gateway.route;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 核心配置类，项目初始化时加载路由配置
 *
 * @author weiyu
 * @date 2024/4/11
 */
@Component
public class GatewayServiceHandler implements ApplicationEventPublisherAware, CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(GatewayServiceHandler.class);

    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;
    @Resource
    private IConfigurationSource configurationSource;
    private ApplicationEventPublisher publisher;

    /**
     * 项目启动时加载路由配置
     */
    @Override
    public void run(String... args) {
        loadRouteConfig();
    }

    /**
     * 加载路由配置
     */
    private void loadRouteConfig() {
        // 从外部配置源加载路由定义
        List<RouteDefinition> definitions = configurationSource.loadRouteDefinitions();
        logger.info("网关配置信息：=====>{}", definitions);
        // 逐个保存路由定义到路由定义写入器中
        definitions.forEach(definition -> routeDefinitionWriter.save(Mono.just(definition)).subscribe());
        // 发布路由刷新事件
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    /**
     * 新增路由
     */
    public void routeAdd(RouteDefinition routeDefinition) {
        logger.info("新增路由：=====>{}", routeDefinition);
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        // 发布路由刷新事件
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    /**
     * 删除路由
     */
    public void routeDelete(String routeId) {
        logger.info("删除路由：=====>{}", routeId);
        routeDefinitionWriter.delete(Mono.just(routeId)).subscribe();
        // 发布路由刷新事件
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    /**
     * 更新路由
     */
    public void routeUpdate(RouteDefinition routeDefinition) {
        logger.info("更新路由：=====>{}", routeDefinition);
        routeDefinitionWriter.delete(Mono.just(routeDefinition.getId())).subscribe();
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        // 发布路由刷新事件
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    /**
     * 刷新路由
     */
    public void routeRefresh() {
        logger.info("刷新路由");
        loadRouteConfig();
    }
}
