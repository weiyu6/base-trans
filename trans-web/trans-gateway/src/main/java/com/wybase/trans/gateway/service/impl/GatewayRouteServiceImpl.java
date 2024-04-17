package com.wybase.trans.gateway.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.ResultCodeEnum;
import com.wybase.trans.common.consts.GatewayConsts;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.gateway.mapper.generate.GatewayRouteMapper;
import com.wybase.trans.gateway.model.dto.RouteInput;
import com.wybase.trans.gateway.model.dto.RouteOutput;
import com.wybase.trans.gateway.model.entity.custom.RouteExtend;
import com.wybase.trans.gateway.model.entity.custom.RouteParamExtend;
import com.wybase.trans.gateway.model.entity.generate.GatewayRoute;
import com.wybase.trans.gateway.model.entity.generate.GatewayRouteParam;
import com.wybase.trans.gateway.model.entity.generate.table.GatewayRouteTableDef;
import com.wybase.trans.gateway.service.IGatewayRouteParamService;
import com.wybase.trans.gateway.service.IGatewayRouteService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 网关路由信息表 服务层实现。
 * @author weiyu
 * @since 2024-04-11
 */
@Service
public class GatewayRouteServiceImpl extends ServiceImpl<GatewayRouteMapper, GatewayRoute> implements IGatewayRouteService {
    private static final Logger logger = LoggerFactory.getLogger(GatewayRouteServiceImpl.class);

    @Resource
    private IGatewayRouteParamService paramService;

    /**
     * 查询网关路由信息表列表。
     * @return 网关路由信息表列表。
     */
    @Override
    public List<GatewayRoute> getRouteList() {
        // 查询条件：状态为启用的路由
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(GatewayRouteTableDef.GATEWAY_ROUTE.ROUTE_STAT.eq(GatewayConsts.ROUTE_STAT_0))
                .and(GatewayRouteTableDef.GATEWAY_ROUTE.RECD_STAT.eq(TransConsts.RECD_STAT_0));
        return list(queryWrapper);
    }

    /**
     * 获取所有路由规则
     */
    @Override
    public RouteOutput getAllRoutes(RouteInput input) {
        RouteOutput routeOutput = new RouteOutput();
        int pageNum = input.getPageNum();
        int pageSize = input.getPageSize();

        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(GatewayRouteTableDef.GATEWAY_ROUTE.RECD_STAT.eq(TransConsts.RECD_STAT_0));
        Page<GatewayRoute> page = new Page<>(pageNum, pageSize);
        Page<GatewayRoute> routeList = page(page, queryWrapper);
        long totalRow = routeList.getTotalRow();
        if (totalRow == 0) {
            logger.error("查询无记录");
            throw new TransException(ResultCodeEnum.ROUTE_NULL_ERROR);
        }
        List<RouteExtend> routeExtendList = new ArrayList<>();
        for (GatewayRoute route : routeList.getRecords()) {
            RouteExtend routeExtend = new RouteExtend();
            BeanUtils.copyProperties(route, routeExtend);
            List<GatewayRouteParam> routeParamList = paramService.routeParamByRouteId(route.getRouteId());
            List<RouteParamExtend> routeParamExtendList = new ArrayList<>();
            for (GatewayRouteParam routeParam : routeParamList) {
                RouteParamExtend routeParamExtend = new RouteParamExtend();
                BeanUtils.copyProperties(routeParam, routeParamExtend);
                routeParamExtendList.add(routeParamExtend);
            }
            routeExtend.setRouteParams(routeParamExtendList);
            routeExtendList.add(routeExtend);
        }

        Page<RouteExtend> routeExtendPage = new Page<>(routeExtendList, pageNum, pageSize, totalRow);
        routeOutput.setRoutePageInfo(routeExtendPage);
        return routeOutput;
    }

    /**
     * 根据路由 ID 删除指定路由规则
     * @param routeId 要删除的路由规则的唯一标识符
     */
    @Override
    public void deleteRouteById(String routeId) {

    }

    /**
     * 刷新所有路由规则，使得新的路由规则立即生效
     */
    @Override
    public void refreshAllRoutes() {

    }

    /**
     * 根据路由 ID 刷新指定路由规则，使得新的路由规则立即生效
     * @param routeId 要刷新的路由规则的唯一标识符
     */
    @Override
    public void refreshRouteById(String routeId) {

    }

    /**
     * 添加新的路由规则
     * @param routeInput
     */
    @Override
    public void addRoute(RouteInput routeInput) {

    }
}
