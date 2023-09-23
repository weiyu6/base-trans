package com.wybase.trans.serve.model.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author weiyu
 * @date 2023/9/23
 */
@Data
@ToString
public class RoleVo {
    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 角色名称
     */
    private String roleNm;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色状态：0-正常，1-停用
     */
    private String roleStat;

    /**
     * 简介
     */
    private String summy;

    /**
     * 角色拥有菜单按钮ID
     */
    private List<String> menuIdList;
}
