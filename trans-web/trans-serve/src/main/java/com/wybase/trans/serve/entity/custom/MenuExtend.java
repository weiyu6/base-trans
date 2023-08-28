package com.wybase.trans.serve.entity.custom;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author weiyu
 * @date 2023/8/28
 */
@Data
@ToString
@NoArgsConstructor
public class MenuExtend {
    /**
     * 菜单ID
     */
    private String menuId;

    /**
     * 上级菜单ID
     */
    private String highLvlId;

    /**
     * 菜单级别
     */
    private String menuLvl;

    /**
     * 菜单名称
     */
    private String menuNm;

    /**
     * 类型(1:菜单,2:按钮)
     */
    private String menuType;

    /**
     * 权限值
     */
    private String permissionValue;

    /**
     * 访问路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序字段，越小越靠前
     */
    private Integer sort;

    /**
     * 菜单状态(0:禁止,1:正常)
     */
    private String menuStat;

    /**
     * 简介
     */
    private String summy;

    /**
     * 外部链接标志(0-否，1-是)
     */
    private String linkFlg;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 记录状态：0-正常，1-删除
     */
    private String recdStat;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 下级菜单
     */
    private List<MenuExtend> children;
}
