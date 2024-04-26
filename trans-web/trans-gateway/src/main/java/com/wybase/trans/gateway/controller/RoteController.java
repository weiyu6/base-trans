package com.wybase.trans.gateway.controller;

import com.wybase.trans.base.result.Result;
import com.wybase.trans.gateway.model.dto.RouteInput;
import com.wybase.trans.gateway.model.dto.RouteOutput;
import com.wybase.trans.gateway.model.entity.generate.GatewayRouteParam;
import com.wybase.trans.gateway.model.vo.RouteVo;
import com.wybase.trans.gateway.service.RouteService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 路由管理
 * @author weiyu
 * @date 2024/4/9
 */
@RestController
@RequestMapping("/gateway/route")
public class RoteController {
    private static final Logger logger = LoggerFactory.getLogger(RoteController.class);

    @Resource
    private RouteService routeService;

    /**
     * 添加路由
     */
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

    /**
     * 刷新路由
     */
    @RequestMapping("/refreshRoute")
    public Result refreshRoute() {
        routeService.refreshAllRoutes();
        return Result.ok();
    }

    /**
     * 删除路由
     */
    @PostMapping("/deleteRouteById")
    public Result deleteRouteById(@RequestParam(name = "routeId") String routeId) {
        routeService.deleteRouteById(routeId);
        return Result.ok();
    }

    /**
     * 根据路由ID刷新路由
     */
    @PostMapping("/refreshRouteById")
    public Result refreshRouteById(@RequestParam(name = "routeId") String routeId) {
        routeService.refreshRouteById(routeId);
        return Result.ok();
    }

    /**
     * 获取路由列表
     */
    @PostMapping("/getRouteList")
    public Result getRouteList(@RequestBody RouteVo vo) {
        RouteInput input = new RouteInput();
        BeanUtils.copyProperties(vo, input);
        RouteOutput routeList = routeService.getAllRoutes(input);
        return Result.ok(routeList);
    }

    /**
     * 更新路由
     */
    @PostMapping("/updateRoute")
    public Result updateRoute(@RequestBody RouteVo vo) {
        RouteInput input = new RouteInput();
        BeanUtils.copyProperties(vo, input);
        List<GatewayRouteParam> routeParams = vo.getRouteParams();
        input.setRouteParams(routeParams);
        routeService.updateRoute(input);
        return Result.ok();
    }

    /**
     * 根据路由ID获取路由
     */
    @PostMapping("/getRouteById")
    public Result getRouteById(@RequestParam(name = "routeId") String routeId) {
        RouteOutput output = routeService.getRouteById(routeId);
        return Result.ok(output.getRouteExtend());
    }

}
