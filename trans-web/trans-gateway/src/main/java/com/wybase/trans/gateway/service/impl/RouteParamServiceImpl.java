package com.wybase.trans.gateway.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.gateway.mapper.generate.RouteParamMapper;
import com.wybase.trans.gateway.model.entity.generate.RouteParam;
import com.wybase.trans.gateway.service.IRouteParamService;
import org.springframework.stereotype.Service;

/**
 * 网关路由参数表 服务层实现。
 *
 * @author weiyu
 * @since 2023-10-25
 */
@Service
public class RouteParamServiceImpl extends ServiceImpl<RouteParamMapper, RouteParam> implements IRouteParamService {

}
