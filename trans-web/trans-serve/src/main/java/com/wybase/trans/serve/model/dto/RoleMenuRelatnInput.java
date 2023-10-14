package com.wybase.trans.serve.model.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author weiyu
 * @date 2023/10/14
 */
@Data
@ToString
public class RoleMenuRelatnInput {
    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 菜单ID列表
     */
    private List<String> menuIdList;
}
