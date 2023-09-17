package com.wybase.trans.serve.service;

import com.mybatisflex.core.service.IService;
import com.wybase.trans.serve.dto.MenuInput;
import com.wybase.trans.serve.dto.MenuOutput;
import com.wybase.trans.serve.entity.generate.Menu;

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
    MenuOutput menuListAll(MenuInput serviceInput);

    /**
     * 查询按钮列表
     */
    MenuOutput buttonList(MenuInput serviceInput);

    /**
     * 根据用户ID获取菜单列表
     * @param userId
     * @return
     */
    List<Menu> menuListByUserId(String userId);
}
