package com.wybase.trans.gateway.model.entity.custom;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 *
 * @author weiyu
 * @date 2024/4/16
 */
@Data
@ToString
public class RouteParamExtend {
    private BigInteger id;

    /**
     * 路由id
     */
    private String routeId;

    /**
     * 参数value
     */
    private String paramValue;

    /**
     * 参数类型：1-predicate，2-filter
     */
    private String paramType;

    /**
     * 路由参数简介
     */
    private String content;

    /**
     * 路由参数状态：0-启用，1-禁用
     */
    private String routeParamStat;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
