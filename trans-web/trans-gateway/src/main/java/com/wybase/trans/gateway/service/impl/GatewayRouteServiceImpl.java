package com.wybase.trans.gateway.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.gateway.mapper.generate.GatewayRouteMapper;
import com.wybase.trans.gateway.model.entity.generate.GatewayRoute;
import com.wybase.trans.gateway.model.entity.generate.table.GatewayRouteTableDef;
import com.wybase.trans.gateway.service.IGatewayRouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 网关路由信息表 服务层实现。
 * @author weiyu
 * @since 2024-04-11
 */
@Service
public class GatewayRouteServiceImpl extends ServiceImpl<GatewayRouteMapper, GatewayRoute> implements IGatewayRouteService {
    private static final Logger logger = LoggerFactory.getLogger(GatewayRouteServiceImpl.class);
    /**
     * 查询网关路由信息表列表。
     * @return 网关路由信息表列表。
     */
    @Override
    public List<GatewayRoute> getRouteList() {
        // 查询条件：状态为启用的路由
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(GatewayRouteTableDef.GATEWAY_ROUTE.RECD_STAT.eq(TransConsts.RECD_STAT_0));
        return list(queryWrapper);
    }
}
