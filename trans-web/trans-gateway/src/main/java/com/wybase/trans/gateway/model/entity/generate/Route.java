package com.wybase.trans.gateway.model.entity.generate;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 网关路由信息表 实体类。
 *
 * @author weiyu
 * @since 2023-10-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "b_route")
public class Route implements Serializable {
    @Id(keyType = KeyType.Auto)
    private Integer id;
    /**
     * 路由ID
     */
    private String routeId;
    /**
     * 路由顺序
     */
    private Integer routeOrder;
    /**
     * 路由路径
     */
    private String url;
    /**
     * 是否有效:0-无效，1-有效
     */
    private Integer valId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
