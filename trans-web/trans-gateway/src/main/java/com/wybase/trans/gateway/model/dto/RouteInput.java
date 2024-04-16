package com.wybase.trans.gateway.model.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author weiyu
 * @date 2024/4/16
 */
@Data
@ToString
public class RouteInput {

    /**
     *页码
     */
    private int pageNum;

    /**
     * 每页显示数量
     */
    private int pageSize;
}
