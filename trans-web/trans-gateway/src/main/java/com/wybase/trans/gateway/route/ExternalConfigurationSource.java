package com.wybase.trans.gateway.route;

import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义路由规则获取源
 * @author weiyu
 * @date 2024/4/9
 */
@Component
public class ExternalConfigurationSource {
    /**
     * 获取路由配置
     * @return
     */
    public List<RouteDefinition> loadRouteDefinitions() {
        // 此处模拟从配置中心获取路由配置
        List<RouteDefinition> definitionList = new ArrayList<>();
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId("base-trans");
        try {
            routeDefinition.setUri(new URI("lb://trans-serve"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        List<PredicateDefinition> predicates = new ArrayList<>();
        predicates.add(new PredicateDefinition("Path=/base-trans/**"));
        routeDefinition.setPredicates(predicates);
        definitionList.add(routeDefinition);
        return definitionList;
    }
}
