package com.wybase.trans.gateway.controller;

import com.wybase.trans.base.result.Result;
import com.wybase.trans.gateway.service.RouteService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author weiyu
 * @date 2024/4/9
 */
@RestController
@RequestMapping("/route")
@Slf4j
public class RoteController {
    @Resource
    private RouteService routeService;

    @RequestMapping("/addRoute")
    public Result addRoute() {
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId("test1");
        try {
            routeDefinition.setUri(new URI("lb://trans-serve"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        List<PredicateDefinition> predicates = new ArrayList<>();
        predicates.add(new PredicateDefinition("Path=/test/**"));
        routeDefinition.setPredicates(predicates);
        routeService.addRoute(routeDefinition);
        return Result.ok();
    }

    @RequestMapping("/refreshRoute")
    public Result refreshRoute() {
        routeService.refreshAllRoutes();
        log.info("刷新路由成功");
        return Result.ok();
    }

}
