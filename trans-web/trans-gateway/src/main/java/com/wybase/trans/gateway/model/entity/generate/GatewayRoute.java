package com.wybase.trans.gateway.model.entity.generate;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 网关路由信息表 实体类。
 *
 * @author weiyu
 * @since 2024-04-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "b_gateway_route")
public class GatewayRoute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private BigInteger id;

    /**
     * 路由id
     */
    private String routeId;

    /**
     * 路由地址
     */
    private String uri;

    /**
     * 路由地址类型：1-lb模式(默认)，2-http模式
     */
    private String uriType;

    /**
     * 排序字段，越小越靠前
     */
    private Integer sort;

    /**
     * 路由简介
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 记录状态：0-正常，1-删除
     */
    private String recdStat;

}
