package com.wybase.trans.gateway.route;

import com.wybase.trans.common.consts.GatewayConsts;
import com.wybase.trans.gateway.model.entity.generate.GatewayRoute;
import com.wybase.trans.gateway.model.entity.generate.GatewayRouteParam;
import com.wybase.trans.gateway.service.IGatewayRouteParamService;
import com.wybase.trans.gateway.service.IGatewayRouteService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * 路由规则MySQL配置源类，用于提供从MySQL数据库获取路由配置信息的功能。
 * @author weiyu
 * @date 2024/4/9
 */
@Component
public class MySQLConfigurationSource implements IConfigurationSource {
    private static final Logger logger = LoggerFactory.getLogger(MySQLConfigurationSource.class);

    @Resource
    private IGatewayRouteService gatewayRouteService;

    @Resource
    private IGatewayRouteParamService gatewayRouteParamService;

    /**
     * 加载路由定义信息。
     * 该方法从数据库中获取路由配置，并将其转换为RouteDefinition对象的列表。
     * @return List<RouteDefinition> 路由定义列表
     */
    @Override
    public List<RouteDefinition> loadRouteDefinitions() {
        logger.info("MySQLConfigurationSource...从MySQL数据库中获取路由配置信息");
        List<RouteDefinition> definitionList = new ArrayList<>();
        // 查询数据库中的路由配置
        List<GatewayRoute> routeList = gatewayRouteService.getRouteList();

        // 遍历查询结果，转换为RouteDefinition对象
        if (!ObjectUtils.isEmpty(routeList)) {
            for (GatewayRoute route : routeList) {
                RouteDefinition routeDefinition = createRouteDefinition(route);
                definitionList.add(routeDefinition);
            }
        }
        return definitionList;
    }

    /**
     * 根据给定的GatewayRoute对象创建路由定义。
     * @param route GatewayRoute对象，包含路由的详细信息。
     * @return RouteDefinition对象，包含了构建好的路由定义。
     */
    public RouteDefinition createRouteDefinition(GatewayRoute route) {
        RouteDefinition routeDefinition = new RouteDefinition();
        String routeId = route.getRouteId();

        // 根据URI类型构建URI对象
        URI uri = (StringUtils.equals(GatewayConsts.ROUTE_URI_TYPE_1, route.getUriType()))
                ? UriComponentsBuilder.fromUriString(route.getUri()).build().toUri()
                : UriComponentsBuilder.fromHttpUrl(route.getUri()).build().toUri();

        // 根据路由ID查询路由参数
        List<GatewayRouteParam> routeParamList = gatewayRouteParamService.routeParamByRouteId(routeId);
        // 解析并设置路由预置条件（predicates）和过滤器（filters）
        List<PredicateDefinition> predicates = new ArrayList<>();
        List<FilterDefinition> filters = new ArrayList<>();
        if (!ObjectUtils.isEmpty(routeParamList)) {
            for (GatewayRouteParam routeParam : routeParamList) {
                // 参数类型：1-predicate，2-filter
                String paramType = routeParam.getParamType();
                String paramValue = routeParam.getParamValue();
                if (GatewayConsts.ROUTE_PARAM_TYPE_1.equals(paramType)) {
                    predicates.add(new PredicateDefinition(paramValue));
                } else if (GatewayConsts.ROUTE_PARAM_TYPE_2.equals(paramType)) {
                    filters.add(new FilterDefinition(paramValue));
                }
            }
        }

        // 设置路由定义属性
        routeDefinition.setId(route.getRouteId());
        routeDefinition.setUri(uri);
        routeDefinition.setPredicates(predicates);
        routeDefinition.setFilters(filters);
        routeDefinition.setOrder(route.getSort());
        return routeDefinition;
    }
}
