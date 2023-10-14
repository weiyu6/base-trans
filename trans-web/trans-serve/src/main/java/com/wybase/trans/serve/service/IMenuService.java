package com.wybase.trans.serve.service;

import com.mybatisflex.core.service.IService;
import com.wybase.trans.serve.model.dto.MenuInput;
import com.wybase.trans.serve.model.dto.MenuOutput;
import com.wybase.trans.serve.model.entity.generate.Menu;

import java.util.List;

/**
 * 菜单表 服务层。
 * @author weiyu
 * @since 2023-08-05
 */
public interface IMenuService extends IService<Menu> {
    /**
     * 查询菜单列表
     * @return
     */
    MenuOutput menuListAll(MenuInput input);

    /**
     * 查询按钮列表
     */
    MenuOutput buttonList(MenuInput input);

    /**
     * 根据用户ID获取菜单列表
     * @param userId
     * @return
     */
    List<Menu> menuListByUserId(String userId);

    /**
     * 根据菜单ID查询菜单信息
     */
    MenuOutput menuInfoQry(MenuInput input);

    /**
     * 根据条件查询菜单列表
     */
    MenuOutput menuList(MenuInput input);

    void menuMdf(MenuInput input);

    /**
     * 获取按钮树形列表
     * @param serviceInput
     * @return
     */
    MenuOutput buttonTree(MenuInput serviceInput);

    /**
     * 添加按钮
     * @param serviceInput
     */
    void buttonAdd(MenuInput serviceInput);

    /**
     * 修改按钮信息
     * @param serviceInput
     */
    void buttonMdf(MenuInput serviceInput);

    /**
     * 菜单删除
     * @param serviceInput
     */
    void menuDel(MenuInput serviceInput);
}
