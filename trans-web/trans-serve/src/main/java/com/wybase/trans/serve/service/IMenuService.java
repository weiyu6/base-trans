package com.wybase.trans.serve.service;

import com.mybatisflex.core.service.IService;
import com.wybase.trans.serve.dto.MenuInput;
import com.wybase.trans.serve.dto.MenuOutput;
import com.wybase.trans.serve.entity.generate.Menu;

/**
 * 菜单表 服务层。
 * @author weiyu
 * @since 2023-08-05
 */
public interface IMenuService extends IService<Menu> {
    /**
     * 查询菜单列表
     * @param serviceInput
     * @return
     */
    MenuOutput menuListAll(MenuInput serviceInput);
}
