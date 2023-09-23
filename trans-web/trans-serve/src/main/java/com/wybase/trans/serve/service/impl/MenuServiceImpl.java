package com.wybase.trans.serve.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.ResultCodeEnum;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.common.util.JwtUtil;
import com.wybase.trans.serve.dao.MenuDao;
import com.wybase.trans.serve.mapper.generate.MenuMapper;
import com.wybase.trans.serve.model.dto.MenuInput;
import com.wybase.trans.serve.model.dto.MenuOutput;
import com.wybase.trans.serve.model.entity.custom.MenuExtend;
import com.wybase.trans.serve.model.entity.generate.Menu;
import com.wybase.trans.serve.model.entity.generate.UserInfo;
import com.wybase.trans.serve.model.entity.generate.table.MenuTableDef;
import com.wybase.trans.serve.service.IMenuService;
import com.wybase.trans.serve.service.IUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单表 服务层实现。
 * @author weiyu
 * @since 2023-08-05
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
    @Value("${util.properties.tokenSignKey:m1e2e3t4b5l6o7g8}")
    private String tokenSignKey;
    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private MenuDao menuDao;

    @Override
    public MenuOutput menuListAll(MenuInput input) {
        MenuOutput serviceOutput = new MenuOutput();
        String token = input.getToken();
        List<MenuExtend> menuExtendList = new ArrayList<>();
        if (StringUtils.isNotEmpty(token)) {
            String userId = JwtUtil.getUserId(token, tokenSignKey);
            // 根据userid查询出用户所拥有的角色
            UserInfo user = userInfoService.getById(userId);
            String roleId = user.getRoleId();
            String[] roleIds = roleId.split("\\|");
            // 用户级别包含2-超级管理员，则查询出所有菜单列表
            if (Arrays.asList(roleIds).contains(TransConsts.ROLE_ID_2)) {
                menuExtendList = menuDao.menuListAll(TransConsts.MENU_TYPE_1, TransConsts.MENU_STAT_1);
            } else {
                // 根据角色ID查询出所拥有的菜单
                menuExtendList = menuDao.menuListByRoleIds(roleIds, TransConsts.MENU_TYPE_1);
            }

        } else {
            menuExtendList = menuDao.menuListAll(TransConsts.MENU_TYPE_1, null);
        }

        List<MenuExtend> finalMenuExtendList = menuExtendList;
        // 使用stream为一级菜单添加子菜单
        List<MenuExtend> menuExtends = finalMenuExtendList.stream()
                // 过滤出菜单级别为1的菜单
                .filter(item -> StringUtils.equals(item.getMenuLvl(), "1"))
                .map(menu -> {
                    // 为1级菜单添加子菜单
                    menu.setChildren(getChildren(menu, finalMenuExtendList));
                    return menu;
                }).sorted((menu1, menu2) -> {
                    return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
                }).collect(Collectors.toList());

        serviceOutput.setMenuTree(menuExtends);
        return serviceOutput;
    }

    /**
     * 查询按钮列表
     */
    @Override
    public MenuOutput buttonList(MenuInput input) {
        logger.debug("MenuServiceImpl.buttonList begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("input:{}", input);
        MenuOutput serviceOutput = new MenuOutput();
        String token = input.getToken();
        List<MenuExtend> menuExtendList = new ArrayList<>();
        if (StringUtils.isNotEmpty(token)) {
            String userId = JwtUtil.getUserId(token, tokenSignKey);
            // 根据userid查询出用户所拥有的角色
            UserInfo user = userInfoService.getById(userId);
            String roleId = user.getRoleId();
            String[] roleIds = roleId.split("\\|");
            // 用户级别包含2-超级管理员，则查询出所有菜单列表,用于显示
            if (Arrays.asList(roleIds).contains(TransConsts.ROLE_ID_2)) {
                menuExtendList = menuDao.menuListAll(TransConsts.MENU_TYPE_2, TransConsts.MENU_STAT_1);
            } else {
                // 根据角色ID查询出所拥有的菜单
                menuExtendList = menuDao.menuListByRoleIds(roleIds, TransConsts.MENU_TYPE_2);
            }

        } else {
            menuExtendList = menuDao.menuListAll(TransConsts.MENU_TYPE_1, null);
        }
        serviceOutput.setButtonList(menuExtendList);
        logger.debug("MenuServiceImpl.buttonList end:<<<<<<<<<<<<<<<<<");
        return serviceOutput;
    }

    @Override
    public List<Menu> menuListByUserId(String userId) {
        // 根据userid查询出用户所拥有的角色
        UserInfo user = userInfoService.getById(userId);
        String roleId = user.getRoleId();
        String[] roleIds = roleId.split("\\|");
        List<Menu> menuExtendList = new ArrayList<>();
        // 用户级别包含2-超级管理员，则查询出所有菜单列表,用于显示
        if (Arrays.asList(roleIds).contains(TransConsts.ROLE_ID_2)) {
            menuExtendList = menuDao.menuListAll1(TransConsts.MENU_TYPE_1, TransConsts.MENU_STAT_1);
        }
        return menuExtendList;
    }

    /**
     * 根据菜单ID查询菜单信息
     */
    @Override
    public MenuOutput menuInfoQry(MenuInput input) {
        logger.debug("MenuServiceImpl.menuInfoQry begin >>>>>>>>>>>>>>>>>>>");
        MenuOutput output = new MenuOutput();
        String menuId = input.getMenuId();
        logger.debug("menuId:{}", menuId);
        Menu menu = getById(menuId);
        if (ObjectUtil.isEmpty(menu)) {
            logger.error("查询无记录");
            throw new TransException(ResultCodeEnum.TRAN100701);
        }
        output.setMenu(menu);
        logger.debug("MenuServiceImpl.menuInfoQry end:<<<<<<<<<<<<<<<<<");
        return output;
    }

    /**
     * 根据条件查询菜单列表
     */
    @Override
    public MenuOutput menuList(MenuInput input) {
        logger.debug("MenuServiceImpl.menuList begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("input:{}", input);
        MenuOutput serviceOutput = new MenuOutput();
        String menuLvl = input.getMenuLvl();// 菜单级别
        String menuNm = input.getMenuNm();
        QueryWrapper query = QueryWrapper.create();
        query.where(MenuTableDef.MENU.MENU_TYPE.eq(TransConsts.MENU_TYPE_1));
        if (StringUtils.isNotBlank(menuLvl)) {
            query.where(MenuTableDef.MENU.MENU_LVL.eq(menuLvl));
        }
        if (StringUtils.isNotBlank(menuNm)) {
            query.where(MenuTableDef.MENU.MENU_NM.like(menuNm));
            query.or(MenuTableDef.MENU.MENU_ID.like(menuNm));
        }
        List<Menu> menuListQry = list(query);
        serviceOutput.setMenuListQry(menuListQry);
        logger.debug("MenuServiceImpl.menuList end:<<<<<<<<<<<<<<<<<");
        return serviceOutput;
    }

    @Override
    public void menuMdf(MenuInput input) {
        logger.debug("MenuServiceImpl.menuMdf begin >>>>>>>>>>>>>>>>>>>");
        Menu menu = new Menu();
        BeanUtils.copyProperties(input, menu);
        logger.debug("menu:{}", menu);
        updateById(menu,true);
        logger.debug("MenuServiceImpl.menuMdf end:<<<<<<<<<<<<<<<<<");
    }

    /**
     * 使用迭代为菜单添加子菜单
     * @param menu
     * @param menuExtendList
     */
    private List<MenuExtend> getChildren(MenuExtend menu, List<MenuExtend> menuExtendList) {
        return menuExtendList.stream()
                .filter(item -> {
                    return StringUtils.equals(item.getHighLvlId(), menu.getMenuId());
                }).map(item -> {
                    item.setChildren(getChildren(item, menuExtendList));
                    return item;
                }).sorted(Comparator.comparingInt(menu2 -> (menu2.getSort() == null ? 0 : menu2.getSort()))).collect(Collectors.toList());
    }

}
