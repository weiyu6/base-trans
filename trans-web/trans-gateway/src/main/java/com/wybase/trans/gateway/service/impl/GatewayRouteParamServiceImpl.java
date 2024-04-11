package com.wybase.trans.gateway.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.gateway.mapper.generate.GatewayRouteParamMapper;
import com.wybase.trans.gateway.model.entity.generate.GatewayRouteParam;
import com.wybase.trans.gateway.model.entity.generate.table.GatewayRouteParamTableDef;
import com.wybase.trans.gateway.service.IGatewayRouteParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 网关路由参数表 服务层实现。
 * @author weiyu
 * @since 2024-04-11
 */
@Service
public class GatewayRouteParamServiceImpl extends ServiceImpl<GatewayRouteParamMapper, GatewayRouteParam> implements IGatewayRouteParamService {
    private static final Logger logger = LoggerFactory.getLogger(GatewayRouteParamServiceImpl.class);

    /**
     * 根据路由ID查询路由参数
     */
    @Override
    public List<GatewayRouteParam> routeParamByRouteId(String routeId) {
        // 查询路由参数
        QueryWrapper query = QueryWrapper.create()
                .where(GatewayRouteParamTableDef.GATEWAY_ROUTE_PARAM.ROUTE_ID.eq(routeId))
                .and(GatewayRouteParamTableDef.GATEWAY_ROUTE_PARAM.RECD_STAT.eq(TransConsts.RECD_STAT_0));
        return list(query);
    }
}
