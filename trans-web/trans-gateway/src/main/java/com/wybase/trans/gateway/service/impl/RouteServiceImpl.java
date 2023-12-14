package com.wybase.trans.gateway.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.gateway.mapper.generate.RouteMapper;
import com.wybase.trans.gateway.model.entity.generate.Route;
import com.wybase.trans.gateway.service.IRouteService;
import org.springframework.stereotype.Service;

/**
 * 网关路由信息表 服务层实现。
 *
 * @author weiyu
 * @since 2023-10-25
 */
@Service
public class RouteServiceImpl extends ServiceImpl<RouteMapper, Route> implements IRouteService {

}
