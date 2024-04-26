package com.wybase.trans.gateway.service.impl;

import cn.hutool.core.util.ObjectUtil;
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
import com.wybase.trans.gateway.model.entity.generate.table.GatewayRouteParamTableDef;
import com.wybase.trans.gateway.model.entity.generate.table.GatewayRouteTableDef;
import com.wybase.trans.gateway.route.GatewayServiceHandler;
import com.wybase.trans.gateway.service.IGatewayRouteParamService;
import com.wybase.trans.gateway.service.IGatewayRouteService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * 网关路由信息表 服务层实现。
 *
 * @author weiyu
 * @since 2024-04-11
 */
@Service
public class GatewayRouteServiceImpl extends ServiceImpl<GatewayRouteMapper, GatewayRoute> implements IGatewayRouteService {
    private static final Logger logger = LoggerFactory.getLogger(GatewayRouteServiceImpl.class);

    @Resource
    private IGatewayRouteParamService paramService;

    @Resource
    private GatewayServiceHandler handler;

    /**
     * 查询网关路由信息表列表。
     *
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
     * 根据输入参数查询网关路由信息，并包装成RouteOutput对象返回。
     *
     * @param input 包含路由查询条件的对象，如路由ID、页码和页大小。
     * @return 返回包含路由信息页面的RouteOutput对象。
     */
    @Override
    public RouteOutput getAllRoutes(RouteInput input) {
        logger.info("GatewayRouteServiceImpl.getAllRoutes begin input:{}", input);
        // 初始化路由输出对象
        RouteOutput routeOutput = new RouteOutput();
        // 从输入中获取路由ID、页码和页大小
        String routeId = input.getRouteId();
        int pageNum = input.getPageNum();
        int pageSize = input.getPageSize();

        // 构造查询条件，只查询状态为0的路由，如果提供了路由ID，则进一步筛选
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(GatewayRouteTableDef.GATEWAY_ROUTE.RECD_STAT.eq(TransConsts.RECD_STAT_0))
                .and(GatewayRouteTableDef.GATEWAY_ROUTE.ROUTE_ID.eq(routeId).when(StringUtils.isNotBlank(routeId)));
        // 创建分页对象
        Page<GatewayRoute> page = new Page<>(pageNum, pageSize);
        // 执行查询
        Page<GatewayRoute> routeList = page(page, queryWrapper);
        // 获取总行数
        long totalRow = routeList.getTotalRow();

        // 遍历查询结果，转换为RouteExtend对象集合
        List<RouteExtend> routeExtendList = new ArrayList<>();
        for (GatewayRoute route : routeList.getRecords()) {
            RouteExtend routeExtend = new RouteExtend();
            // 使用BeanUtils复制属性
            BeanUtils.copyProperties(route, routeExtend);
            // 查询该路由相关的参数信息
            List<GatewayRouteParam> routeParamList = paramService.routeParamByRouteId(route.getRouteId());
            // 将路由参数转换为RouteParamExtend对象集合
            List<RouteParamExtend> routeParamExtendList = new ArrayList<>();
            for (GatewayRouteParam routeParam : routeParamList) {
                RouteParamExtend routeParamExtend = new RouteParamExtend();
                BeanUtils.copyProperties(routeParam, routeParamExtend);
                routeParamExtendList.add(routeParamExtend);
            }
            // 设置路由参数到RouteExtend对象
            routeExtend.setRouteParams(routeParamExtendList);
            routeExtendList.add(routeExtend);
        }

        // 创建RouteExtend的分页对象
        Page<RouteExtend> routeExtendPage = new Page<>(routeExtendList, pageNum, pageSize, totalRow);
        // 设置路由信息页面到routeOutput对象
        routeOutput.setRoutePageInfo(routeExtendPage);
        logger.info("GatewayRouteServiceImpl.getAllRoutes end:{}", routeOutput);
        return routeOutput;
    }

    /**
     * 根据路由 ID 删除指定路由规则
     *
     * @param routeId 要删除的路由规则的唯一标识符
     */
    @Override
    public void deleteRouteById(String routeId) {
        logger.info("GatewayRouteServiceImpl.deleteRouteById:{}", routeId);
        // 查询现有路由信息
        QueryWrapper wrapper = QueryWrapper.create()
                .where(GatewayRouteTableDef.GATEWAY_ROUTE.ROUTE_ID.eq(routeId))
                .and(GatewayRouteTableDef.GATEWAY_ROUTE.RECD_STAT.eq(TransConsts.RECD_STAT_0));
        GatewayRoute route = getOne(wrapper);
        if (ObjectUtils.isEmpty(route)) {
            logger.error("{}：路由参数不存在", routeId);
            throw new TransException(ResultCodeEnum.ROUTE_NULL_ERROR);
        }
        route.setRecdStat(TransConsts.RECD_STAT_1);
        updateById(route);
        // 删除对应的路由参数
        QueryWrapper paramQuery = QueryWrapper.create()
                .where(GatewayRouteParamTableDef.GATEWAY_ROUTE_PARAM.ROUTE_ID.eq(routeId));
        paramService.remove(paramQuery);
        handler.routeDelete(routeId);
    }

    /**
     * 刷新所有路由规则，使得新的路由规则立即生效
     */
    @Override
    public void refreshAllRoutes() {
        logger.info("重新读取路由配置进行发布");
        handler.routeRefresh();
    }

    /**
     * 根据路由 ID 刷新指定路由规则，使得新的路由规则立即生效
     *
     * @param routeId 要刷新的路由规则的唯一标识符
     */
    @Override
    public void refreshRouteById(String routeId) {
        logger.info("GatewayRouteServiceImpl.refreshRouteById begin routeId:{}", routeId);
        if (StringUtils.isBlank(routeId)) {
            logger.info("路由ID不能为空");
            throw new TransException(ResultCodeEnum.NULL_ERROR, "路由ID不能为空");
        }
        // 查询现有路由信息
        QueryWrapper wrapper = QueryWrapper.create()
                .where(GatewayRouteTableDef.GATEWAY_ROUTE.ROUTE_ID.eq(routeId))
                .and(GatewayRouteTableDef.GATEWAY_ROUTE.RECD_STAT.eq(TransConsts.RECD_STAT_0));
        GatewayRoute route = getOne(wrapper);
        if (ObjectUtils.isEmpty(route)) {
            logger.error("{}：路由参数不存在", routeId);
            throw new TransException(ResultCodeEnum.ROUTE_NULL_ERROR);
        }
        if (StringUtils.equals(GatewayConsts.ROUTE_STAT_0, route.getRouteStat())) {
            logger.error("{}：路由未启用", routeId);
            throw new TransException(ResultCodeEnum.ROUTE_STAT_NOT_ENABLE);
        }
        // 查询对应的路由参数
        QueryWrapper paramQuery = QueryWrapper.create()
                .where(GatewayRouteParamTableDef.GATEWAY_ROUTE_PARAM.ROUTE_ID.eq(routeId))
                .and(GatewayRouteParamTableDef.GATEWAY_ROUTE_PARAM.ROUTE_PARAM_STAT.eq(GatewayConsts.ROUTE_PARAM_STAT_0))
                .and(GatewayRouteParamTableDef.GATEWAY_ROUTE_PARAM.RECD_STAT.eq(TransConsts.RECD_STAT_0));
        List<GatewayRouteParam> routeParamList = paramService.list(paramQuery);
        handler.routeUpdate(creatRouteDefinition(route, routeParamList));
    }

    /**
     * 根据输入的路由信息更新或创建路由及路由参数。
     *
     * @param routeInput 包含路由ID、URI、URI类型、排序、内容和参数列表的信息输入对象。
     * @throws TransException 如果路由ID为空或已存在，则抛出异常。
     */
    @Override
    public void addRoute(RouteInput routeInput) {
        logger.info("GatewayRouteServiceImpl.addRoute:{}", routeInput);
        // 从输入中获取路由相关参数
        String routeId = routeInput.getRouteId();
        String uri = routeInput.getUri();
        String uriType = routeInput.getUriType();
        Integer sort = routeInput.getSort();
        String content = routeInput.getContent();
        List<GatewayRouteParam> routeParams = routeInput.getRouteParams();

        // 验证路由ID不能为空
        if (StringUtils.isBlank(routeId)) {
            logger.info("路由ID不能为空");
            throw new TransException(ResultCodeEnum.NULL_ERROR, "路由ID不能为空");
        }

        // 查询现有路由信息
        QueryWrapper wrapper = QueryWrapper.create()
                .where(GatewayRouteTableDef.GATEWAY_ROUTE.ROUTE_ID.eq(routeId));
        GatewayRoute route = getOne(wrapper);

        // 如果路由已存在，则更新路由信息和参数；否则，创建新路由并保存参数
        if (ObjectUtil.isNotEmpty(route)) {
            // 检查路由状态，若已存在且为不可用状态，则抛出异常
            if (StringUtils.equals(TransConsts.RECD_STAT_0, route.getRecdStat())) {
                logger.error("路由ID已存在");
                throw new TransException(ResultCodeEnum.ROUTE_NULL_ERROR, "路由ID已存在");
            }
            // 更新路由信息
            route.setUri(uri);
            route.setUriType(uriType);
            route.setContent(content);
            route.setSort(sort);
            updateById(route);

            // 删除并重新保存路由参数
            QueryWrapper paramQuery = QueryWrapper.create()
                    .where(GatewayRouteParamTableDef.GATEWAY_ROUTE_PARAM.ROUTE_ID.eq(routeId));
            paramService.remove(paramQuery);
            paramService.saveBatch(routeParams);
        } else {
            // 创建新路由并保存参数
            route = new GatewayRoute();
            route.setRouteId(routeId);
            route.setUri(uri);
            route.setUriType(uriType);
            route.setContent(content);
            route.setSort(sort);
            save(route);
            paramService.saveBatch(routeParams);
        }
        // 触发路由更新处理
        handler.routeUpdate(creatRouteDefinition(route, routeParams));
    }

    /**
     * 创建路由定义对象
     */
    private RouteDefinition creatRouteDefinition(GatewayRoute route, List<GatewayRouteParam> routeParams) {
        RouteDefinition routeDefinition = new RouteDefinition();

        // 根据URI类型构建URI对象
        URI uri = (StringUtils.equals(GatewayConsts.ROUTE_URI_TYPE_1, route.getUriType()))
                ? UriComponentsBuilder.fromUriString(route.getUri()).build().toUri()
                : UriComponentsBuilder.fromHttpUrl(route.getUri()).build().toUri();

        // 解析并设置路由预置条件（predicates）和过滤器（filters）
        List<PredicateDefinition> predicates = new ArrayList<>();
        List<FilterDefinition> filters = new ArrayList<>();
        if (!ObjectUtils.isEmpty(routeParams)) {
            for (GatewayRouteParam routeParam : routeParams) {
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
