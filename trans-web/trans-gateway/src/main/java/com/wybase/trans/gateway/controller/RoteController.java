package com.wybase.trans.gateway.controller;

import com.wybase.trans.base.result.Result;
import com.wybase.trans.gateway.model.dto.RouteInput;
import com.wybase.trans.gateway.model.dto.RouteOutput;
import com.wybase.trans.gateway.model.entity.generate.GatewayRouteParam;
import com.wybase.trans.gateway.model.vo.RouteVo;
import com.wybase.trans.gateway.service.IGatewayRouteService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 路由管理
 *
 * @author weiyu
 * @date 2024/4/9
 */
@RestController
@RequestMapping("/gateway/route")
public class RoteController {
    private static final Logger logger = LoggerFactory.getLogger(RoteController.class);

    @Resource
    private IGatewayRouteService routeService;

    @PostMapping("/addRoute")
    public Result addRoute(@RequestBody RouteVo vo) {
        logger.info("RoteController.addRoute ,routeVo:{}", vo);
        RouteInput routeInput = new RouteInput();
        List<GatewayRouteParam> routeParams = vo.getRouteParams();
        BeanUtils.copyProperties(vo, routeInput);
        routeInput.setRouteParams(routeParams);
        routeService.addRoute(routeInput);
        return Result.ok();
    }

    @RequestMapping("/refreshRoute")
    public Result refreshRoute() {
        routeService.refreshAllRoutes();
        return Result.ok();
    }

    @PostMapping("/deleteRouteById")
    public Result deleteRouteById(String routeId) {
        routeService.deleteRouteById(routeId);
        return Result.ok();
    }

    @PostMapping("/refreshRouteById")
    public Result refreshRouteById(@RequestParam String routeId) {
        routeService.refreshRouteById(routeId);
        return Result.ok();
    }


    @PostMapping("/getRouteList")
    public Result getRouteList(@RequestBody RouteVo vo) {
        RouteInput input = new RouteInput();
        BeanUtils.copyProperties(vo, input);
        RouteOutput routeList = routeService.getAllRoutes(input);
        return Result.ok(routeList);
    }

    @PostMapping("/updateRoute")
    public Result updateRoute(@RequestBody RouteVo vo) {
        return Result.ok();
    }

    @PostMapping("/getRouteById")
    public Result getRouteById(@RequestParam String routeId) {
        return Result.ok();
    }

}
