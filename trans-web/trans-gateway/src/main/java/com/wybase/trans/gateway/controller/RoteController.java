package com.wybase.trans.gateway.controller;

import com.wybase.trans.base.result.Result;
import com.wybase.trans.gateway.model.dto.RouteInput;
import com.wybase.trans.gateway.model.dto.RouteOutput;
import com.wybase.trans.gateway.model.vo.RouteVo;
import com.wybase.trans.gateway.service.IGatewayRouteService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weiyu
 * @date 2024/4/9
 */
@RestController
@RequestMapping("/gateway/route")
public class RoteController {
    private static final Logger logger = LoggerFactory.getLogger(RoteController.class);

    @Resource
    private IGatewayRouteService routeService;

    @RequestMapping("/addRoute")
    public Result addRoute(@RequestBody RouteVo vo) {
        RouteInput routeInput = new RouteInput();
        routeService.addRoute(routeInput);
        return Result.ok();
    }

    @RequestMapping("/refreshRoute")
    public Result refreshRoute() {
        routeService.refreshAllRoutes();
        logger.info("刷新路由成功");
        return Result.ok();
    }

    public Result deleteRouteById(String routeId) {
        return Result.ok();
    }

    public Result refreshRouteById(String routeId) {
        return Result.ok();
    }


    @RequestMapping("/getRouteList")
    public Result getRouteList(@RequestBody RouteVo vo) {
        RouteInput input = new RouteInput();
        BeanUtils.copyProperties(vo, input);
        RouteOutput routeList = routeService.getAllRoutes(input);
        return Result.ok(routeList);
    }

    public Result updateRouteById(String routeId) {
        return Result.ok();
    }

    public Result getRouteById(String routeId) {
        return Result.ok();
    }

}
