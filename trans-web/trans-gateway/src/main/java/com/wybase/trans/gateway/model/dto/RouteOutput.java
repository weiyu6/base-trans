package com.wybase.trans.gateway.model.dto;

import com.mybatisflex.core.paginate.Page;
import com.wybase.trans.gateway.model.entity.custom.RouteExtend;
import lombok.Data;
import lombok.ToString;

/**
 * @author weiyu
 * @date 2024/4/16
 */
@Data
@ToString
public class RouteOutput {

    private Page<RouteExtend> routePageInfo;

}
