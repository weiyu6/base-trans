package com.wybase.trans.gateway.route;

import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.List;

/**
 * 路由规则源
 * @author weiyu
 * @date 2024/4/11
 */
public interface IConfigurationSource {
    List<RouteDefinition> loadRouteDefinitions();
}
