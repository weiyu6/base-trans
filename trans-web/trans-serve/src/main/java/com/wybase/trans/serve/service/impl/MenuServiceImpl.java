package com.wybase.trans.serve.service.impl;

import cn.hutool.core.util.IdUtil;
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
import java.util.concurrent.CopyOnWriteArrayList;
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

    @Autowired
    private MenuMapper menuMapper;

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
                }).sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort()))).collect(Collectors.toList());

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
        updateById(menu, true);
        logger.debug("MenuServiceImpl.menuMdf end:<<<<<<<<<<<<<<<<<");
    }

    /**
     * 获取按钮树形列表
     * @param serviceInput
     * @return
     */
    @Override
    public MenuOutput buttonTree(MenuInput serviceInput) {
        logger.debug("MenuServiceImpl.buttonTree begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("serviceInput:{}", serviceInput);
        List<Menu> menuList = new ArrayList<>();
        String menuId = serviceInput.getMenuId();

        QueryWrapper queryWrapper = QueryWrapper.create();
        // 如果菜单ID为空，则查询全部列表
        if (StringUtils.isEmpty(menuId)) {

            queryWrapper.where(MenuTableDef.MENU.MENU_LVL.ne(TransConsts.MENU_LVL_1))
                    .where(MenuTableDef.MENU.LINK_FLG.eq("0"))
                    .where(MenuTableDef.MENU.RECD_STAT.eq(TransConsts.RECD_STAT_0));
            menuList = menuMapper.selectListByQuery(queryWrapper);
            logger.debug("menuExtendList:{}", menuList);

        } else {
            queryWrapper.where(MenuTableDef.MENU.MENU_ID.eq(menuId))
                    .or(MenuTableDef.MENU.HIGH_LVL_ID.eq(menuId));
            menuList = menuMapper.selectListByQuery(queryWrapper);
        }
        List<MenuExtend> menuExtendList = new CopyOnWriteArrayList<>();
        // 将menu中的值拷贝到menuExtend中
        for (Menu menu : menuList) {
            MenuExtend menuExtend = new MenuExtend();
            BeanUtils.copyProperties(menu, menuExtend);
            menuExtendList.add(menuExtend);
        }
        // 对菜单列表进行重排，生成树形列表
        List<MenuExtend> buttonTree = menuExtendList.stream()
                .filter(menu -> StringUtils.equals(menu.getMenuLvl(), TransConsts.MENU_LVL_2))
                .map(menu -> {
                    menu.setChildren(getChildren(menu, menuExtendList));
                    return menu;
                }).sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort()))).collect(Collectors.toList());

        // 只返回存在按钮的菜单列表
        buttonTree = buttonTree.stream()
                .filter(menu -> ObjectUtil.isNotEmpty(menu.getChildren()))
                .collect(Collectors.toList());

        logger.debug("将按钮及其上级菜单集合存入Redis缓存中");
        MenuOutput serviceOutput = new MenuOutput();
        serviceOutput.setButtonTree(buttonTree);
        logger.debug("MenuServiceImpl.buttonTree end:<<<<<<<<<<<<<<<<<");
        return serviceOutput;
    }

    @Override
    public void buttonAdd(MenuInput serviceInput) {
        logger.debug("MenuServiceImpl.buttonAdd begin >>>>>>>>>>>>>>>>>>>");
        String highLvlId = serviceInput.getHighLvlId();
        // 上级菜单ID为空，代表此菜单为一级菜单
        if (StringUtils.isEmpty(highLvlId) || StringUtils.equals(highLvlId, "0")) {
            logger.error("上级菜单ID不能为空");
            throw new TransException(ResultCodeEnum.NULL_ERROR, "上级菜单ID不能为空");
        }
        Menu menu = getById(highLvlId);
        if (ObjectUtil.isEmpty(menu)) {
            logger.error("上级菜单ID：{}查询为空", highLvlId);
            throw new TransException(ResultCodeEnum.TRAN100701);
        }
        String menuLvl = menu.getMenuLvl();
        menuLvl = String.valueOf(Integer.valueOf(menuLvl) + 1);

        long snowFlakeId = IdUtil.getSnowflakeNextId();
        String menuId = String.format("m%s", snowFlakeId);
        logger.debug("菜单ID:{}", menuId);

        menu = new Menu();
        BeanUtils.copyProperties(serviceInput, menu);
        menu.setMenuId(menuId);
        menu.setHighLvlId(highLvlId);
        menu.setMenuLvl(menuLvl);
        logger.debug("menu:{}", menu);
        save(menu);
        logger.debug("MenuServiceImpl.buttonAdd end:<<<<<<<<<<<<<<<<<");
    }

    @Override
    public void buttonMdf(MenuInput serviceInput) {
        logger.debug("MenuServiceImpl.buttonMdf begin >>>>>>>>>>>>>>>>>>>");
        Menu menu = new Menu();
        BeanUtils.copyProperties(serviceInput, menu);
        logger.debug("menu:{}", menu);
        updateById(menu, true);
        logger.debug("MenuServiceImpl.buttonMdf end:<<<<<<<<<<<<<<<<<");
    }

    @Override
    public void menuDel(MenuInput serviceInput) {
        logger.debug("MenuServiceImpl.menuDel begin >>>>>>>>>>>>>>>>>>>");
        String menuId = serviceInput.getMenuId();
        Menu menu = getById(menuId);
        if (ObjectUtil.isEmpty(menu)) {
            logger.error("menuId:{}查询无记录", menuId);
            throw new TransException(ResultCodeEnum.TRAN100701);
        }

        menu.setRecdStat(TransConsts.RECD_STAT_1);
        updateById(menu, true);
        logger.debug("MenuServiceImpl.menuDel end:<<<<<<<<<<<<<<<<<");
    }

    @Override
    public MenuOutput menuButtonTree(MenuInput serviceInput) {
        logger.debug("MenuServiceImpl.menuButtonTree begin >>>>>>>>>>>>>>>>>>>");
        MenuOutput serviceOutput = new MenuOutput();
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(MenuTableDef.MENU.RECD_STAT.eq(TransConsts.RECD_STAT_0));
        List<Menu> menuList = list(queryWrapper);

        List<MenuExtend> menuExtendList = new CopyOnWriteArrayList<>();
        // 将menu中的值拷贝到menuExtend中
        for (Menu menu : menuList) {
            MenuExtend menuExtend = new MenuExtend();
            BeanUtils.copyProperties(menu, menuExtend);
            menuExtendList.add(menuExtend);
        }
        // 使用stream为一级菜单添加子菜单
        List<MenuExtend> menuButtonList = menuExtendList.stream()
                // 过滤出菜单级别为1的菜单
                .filter(item -> StringUtils.equals(item.getMenuLvl(), "1"))
                .map(menu -> {
                    // 为1级菜单添加子菜单
                    menu.setChildren(getChildren(menu, menuExtendList));
                    return menu;
                }).sorted((menu1, menu2) -> {
                    return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
                }).collect(Collectors.toList());
        logger.debug("MenuServiceImpl.menuButtonTree end:<<<<<<<<<<<<<<<<<");
        serviceOutput.setMenuButtonTree(menuButtonList);
        return serviceOutput;
    }

    /**
     * 使用迭代为菜单添加子菜单
     * @param menu
     * @param menuExtendList
     */
    private List<MenuExtend> getChildren(MenuExtend menu, List<MenuExtend> menuExtendList) {
        return menuExtendList.stream()
                .filter(item -> StringUtils.equals(item.getHighLvlId(), menu.getMenuId())).map(item -> {
                    item.setChildren(getChildren(item, menuExtendList));
                    return item;
                }).sorted(Comparator.comparingInt(menu2 -> (menu2.getSort() == null ? 0 : menu2.getSort()))).collect(Collectors.toList());
    }

}
