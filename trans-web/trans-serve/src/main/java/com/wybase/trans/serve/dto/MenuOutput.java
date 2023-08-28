package com.wybase.trans.serve.dto;

import com.alibaba.fastjson2.JSONObject;
import com.wybase.trans.serve.entity.custom.MenuExtend;
import com.wybase.trans.serve.entity.generate.Menu;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author weiyu
 * @date 2023/8/28
 */
@Data
@ToString
@NoArgsConstructor
public class MenuOutput {
    /**
     * 菜单列表
     */
    private List<JSONObject> menuList;

    /**
     * 菜单树形结构
     */
    private List<MenuExtend> menuTree;

    /**
     * 菜单列表
     */
    private List<Menu> menuListQry;

    /**
     * 按钮树形结构
     */
    private List<MenuExtend> buttonList;

    /**
     * 菜单信息
     */
    private Menu menu;

    /**
     * 按钮树形结构
     */
    private List<MenuExtend> buttonTree;
    /**
     * 菜单按钮树形列表
     */
    private List<MenuExtend> menuButtonTree;
}
