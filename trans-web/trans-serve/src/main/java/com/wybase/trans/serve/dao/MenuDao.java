package com.wybase.trans.serve.dao;

import com.mybatisflex.core.query.QueryWrapper;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.serve.mapper.generate.MenuMapper;
import com.wybase.trans.serve.model.entity.custom.MenuExtend;
import com.wybase.trans.serve.model.entity.generate.Menu;
import com.wybase.trans.serve.model.entity.generate.table.MenuTableDef;
import com.wybase.trans.serve.model.entity.generate.table.RoleMenuRelatnTableDef;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.mybatisflex.core.query.QueryMethods.distinct;
import static com.mybatisflex.core.query.QueryMethods.noCondition;
import static com.mybatisflex.core.query.QueryMethods.select;

/**
 * @author weiyu
 * @date 2023/8/28
 */
@Component
public class MenuDao {
    @Autowired
    private MenuMapper mapper;

    public List<MenuExtend> menuListAll(String menuType, String menuStat) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.where(MenuTableDef.MENU.MENU_TYPE.eq(menuType))
                .where(StringUtils.isNoneBlank(menuStat) ? MenuTableDef.MENU.MENU_STAT.eq(menuStat) : noCondition())
                .where(MenuTableDef.MENU.RECD_STAT.eq(TransConsts.RECD_STAT_0));
        List<Menu> menuList = mapper.selectListByQuery(wrapper);

        return menuList.stream().map(menu -> {
            MenuExtend menuExtend = new MenuExtend();
            BeanUtils.copyProperties(menu, menuExtend);
            return menuExtend;
        }).collect(Collectors.toList());
    }

    public List<Menu> menuListAll1(String menuType, String menuStat) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.where(MenuTableDef.MENU.MENU_TYPE.eq(menuType))
                .where(StringUtils.isNoneBlank(menuStat) ? MenuTableDef.MENU.MENU_STAT.eq(menuStat) : noCondition())
                .where(MenuTableDef.MENU.RECD_STAT.eq(TransConsts.RECD_STAT_0));
        return mapper.selectListByQuery(wrapper);
    }

    public List<MenuExtend> menuListByRoleIds(String[] roleIds, String menuType1) {
        QueryWrapper wrapper = QueryWrapper.create()
                .from(MenuTableDef.MENU)
                .where(MenuTableDef.MENU.MENU_ID.in(
                        select(distinct(RoleMenuRelatnTableDef.ROLE_MENU_RELATN.MENU_ID))
                                .from(RoleMenuRelatnTableDef.ROLE_MENU_RELATN)
                                .where(RoleMenuRelatnTableDef.ROLE_MENU_RELATN.ROLE_ID.in(roleIds))
                                .and(RoleMenuRelatnTableDef.ROLE_MENU_RELATN.RECD_STAT.eq(TransConsts.RECD_STAT_0))
                ))
                .and(MenuTableDef.MENU.MENU_TYPE.eq(menuType1))
                .and(MenuTableDef.MENU.MENU_STAT.eq("1"))
                .and(MenuTableDef.MENU.RECD_STAT.eq(TransConsts.RECD_STAT_0));
        List<Menu> menuList = mapper.selectListByQuery(wrapper);

        return menuList.stream().map(menu -> {
            MenuExtend menuExtend = new MenuExtend();
            BeanUtils.copyProperties(menu, menuExtend);
            return menuExtend;
        }).collect(Collectors.toList());
    }
}
