package com.wybase.trans.serve.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.ResultCodeEnum;
import com.wybase.trans.serve.mapper.generate.RoleMenuRelatnMapper;
import com.wybase.trans.serve.model.dto.RoleMenuRelatnInput;
import com.wybase.trans.serve.model.entity.generate.RoleMenuRelatn;
import com.wybase.trans.serve.model.entity.generate.table.RoleMenuRelatnTableDef;
import com.wybase.trans.serve.service.IRoleMenuRelatnService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色菜单关联表 服务层实现。
 * @author weiyu
 * @since 2023-08-05
 */
@Service
public class RoleMenuRelatnServiceImpl extends ServiceImpl<RoleMenuRelatnMapper, RoleMenuRelatn> implements IRoleMenuRelatnService {
    private static final Logger logger = LoggerFactory.getLogger(RoleMenuRelatnServiceImpl.class);

    /**
     * 根据角色id查询关联表列表
     * @param roleId
     */
    @Override
    public List<RoleMenuRelatn> roleMenuByRoleId(String roleId) {
        logger.debug("RoleMenuRelatnServiceImpl.roleMenuByRoleId begin >>>>>>>>>>>>>>>>>>>");
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(RoleMenuRelatnTableDef.ROLE_MENU_RELATN.ROLE_ID.eq(roleId));
        List<RoleMenuRelatn> roleMenuRelatnList = list(queryWrapper);
        logger.debug("roleMenuRelatnList:{}", roleMenuRelatnList);
        logger.debug("RoleMenuRelatnServiceImpl.roleMenuByRoleId end:<<<<<<<<<<<<<<<<<");
        return roleMenuRelatnList;
    }

    /**
     * 根据roleId删除相关数据
     * @param relatnServiceInput
     */
    @Override
    public void delByRoleId(RoleMenuRelatnInput relatnServiceInput) {
        logger.debug("RoleMenuRelatnServiceImpl.delByRoleId begin >>>>>>>>>>>>>>>>>>>");
        String roleId = relatnServiceInput.getRoleId();
        if (StringUtils.isEmpty(roleId)) {
            logger.error("角色ID或者菜单ID不能为空");
            throw new TransException(ResultCodeEnum.NULL_ERROR, "角色ID不能为空");
        }
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(RoleMenuRelatnTableDef.ROLE_MENU_RELATN.ROLE_ID.eq(roleId));
        remove(queryWrapper);
        logger.debug("RoleMenuRelatnServiceImpl.delByRoleId end:<<<<<<<<<<<<<<<<<");
    }

    /**
     * 插入角色与菜单对应关系
     * @param relatnServiceInput
     */
    @Override
    public void listAdd(RoleMenuRelatnInput relatnServiceInput) {
        logger.debug("RoleMenuRelatnServiceImpl.listAdd begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("relatnServiceInput:{}", relatnServiceInput);
        String roleId = relatnServiceInput.getRoleId();
        List<String> menuIdList = relatnServiceInput.getMenuIdList();
        if (StringUtils.isEmpty(roleId) || ObjectUtil.isEmpty(menuIdList)) {
            logger.error("角色ID或者菜单ID不能为空");
            throw new TransException(ResultCodeEnum.NULL_ERROR, "角色ID或者菜单ID不能为空");
        }
        for (String menuId : menuIdList) {
            long id = IdUtil.getSnowflakeNextId();
            String rmId = String.format("rm%s", id);
            RoleMenuRelatn relatn = new RoleMenuRelatn();
            relatn.setRmrId(rmId);
            relatn.setRoleId(roleId);
            relatn.setMenuId(menuId);
            save(relatn);
        }
        logger.debug("RoleMenuRelatnServiceImpl.listAdd end:<<<<<<<<<<<<<<<<<");
    }
}
