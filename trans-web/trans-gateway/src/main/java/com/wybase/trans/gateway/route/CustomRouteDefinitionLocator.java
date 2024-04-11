//package com.wybase.trans.gateway.route;
//
//import jakarta.annotation.Resource;
//import org.springframework.cloud.gateway.route.RouteDefinition;
//import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Flux;
//
//import java.util.List;
//
///**
// * 自定义路由定义，用于从外部源加载路由规则
// * @author weiyu
// * @date 2024/4/9
// */
//@Component
//public class CustomRouteDefinitionLocator implements RouteDefinitionLocator {
//    @Resource
//    private ExternalConfigurationSource externalConfigurationSource;
//
//    /**
//     * 获取路由定义
//     * @return
//     */
//    @Override
//    public Flux<RouteDefinition> getRouteDefinitions() {
//        List<RouteDefinition> definitions = externalConfigurationSource.loadRouteDefinitions();
//        return Flux.fromIterable(definitions);
//    }
//}
