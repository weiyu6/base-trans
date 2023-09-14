package com.wybase.trans.serve.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.common.util.JwtUtil;
import com.wybase.trans.serve.dao.MenuDao;
import com.wybase.trans.serve.dto.MenuInput;
import com.wybase.trans.serve.dto.MenuOutput;
import com.wybase.trans.serve.entity.custom.MenuExtend;
import com.wybase.trans.serve.entity.generate.Menu;
import com.wybase.trans.serve.entity.generate.UserInfo;
import com.wybase.trans.serve.mapper.generate.MenuMapper;
import com.wybase.trans.serve.service.IMenuService;
import com.wybase.trans.serve.service.IUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public MenuOutput menuListAll(MenuInput serviceInput) {
        MenuOutput serviceOutput = new MenuOutput();
        String token = serviceInput.getToken();
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
    public MenuOutput buttonList(MenuInput serviceInput) {
        logger.debug("MenuServiceImpl.buttonList begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("serviceInput:{}", serviceInput);
        MenuOutput serviceOutput = new MenuOutput();
        String token = serviceInput.getToken();
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
