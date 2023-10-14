package com.wybase.trans.serve.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.ResultCodeEnum;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.serve.mapper.generate.RoleMapper;
import com.wybase.trans.serve.model.dto.RoleInput;
import com.wybase.trans.serve.model.dto.RoleMenuRelatnInput;
import com.wybase.trans.serve.model.dto.RoleOutput;
import com.wybase.trans.serve.model.entity.generate.Role;
import com.wybase.trans.serve.model.entity.generate.RoleMenuRelatn;
import com.wybase.trans.serve.model.entity.generate.table.RoleTableDef;
import com.wybase.trans.serve.service.IRoleMenuRelatnService;
import com.wybase.trans.serve.service.IRoleService;
import com.wybase.trans.serve.util.RecdStatUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色表 服务层实现。
 * @author weiyu
 * @since 2023-08-05
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    @Autowired
    private IRoleMenuRelatnService roleMenuRelatnService;

    /**
     * 查询角色列表
     */
    @Override
    public RoleOutput roleList(RoleInput input) {
        logger.debug("RoleServiceImpl.roleList begin >>>>>>>>>>>>>>>>>>>");
        RoleOutput serviceOutput = new RoleOutput();
        String roleNm = input.getRoleNm();
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(RoleTableDef.ROLE.RECD_STAT.eq(TransConsts.RECD_STAT_0));
        if (StringUtils.isNotBlank(roleNm)) {
            queryWrapper.where(RoleTableDef.ROLE.ROLE_NM.eq(roleNm));
        }
        List<Role> roleList = list(queryWrapper);
        serviceOutput.setRoleList(roleList);
        logger.debug("RoleServiceImpl.roleList end:<<<<<<<<<<<<<<<<<");
        return serviceOutput;
    }

    /**
     * 根据ID查询角色信息
     * @param serviceInput
     * @return
     */
    @Override
    public RoleOutput roleInfoQry(RoleInput serviceInput) {
        logger.debug("RoleServiceImpl.roleInfoQry begin >>>>>>>>>>>>>>>>>>>");
        String roleId = serviceInput.getRoleId();
        if (StringUtils.isEmpty(roleId)) {
            logger.error("角色ID不能为空");
            throw new TransException(ResultCodeEnum.NULL_ERROR, "角色ID不能为空");
        }
        Role role = getById(roleId);
        if (ObjectUtil.isEmpty(role)) {
            logger.error("查询无记录");
            throw new TransException(ResultCodeEnum.TRAN100701);
        }
        RecdStatUtil.recdStatChk(role.getRecdStat());
        List<RoleMenuRelatn> roleMenuRelatnList = new ArrayList<>();
        roleMenuRelatnList = roleMenuRelatnService.roleMenuByRoleId(roleId);

        RoleOutput serviceOutput = new RoleOutput();
        serviceOutput.setRoleInfo(role);
        serviceOutput.setRoleMenuRelatnList(roleMenuRelatnList);
        logger.debug("RoleServiceImpl.roleInfoQry end:<<<<<<<<<<<<<<<<<");
        return serviceOutput;
    }

    /**
     * 角色信息修改
     * @param serviceInput
     */
    @Override
    public void roleMdf(RoleInput serviceInput) {
        logger.debug("RoleServiceImpl.roleMdf begin >>>>>>>>>>>>>>>>>>>");

        String roleId = serviceInput.getRoleId();

        Role role = new Role();
        BeanUtils.copyProperties(serviceInput, role);
        updateById(role, true);
        List<String> menuIdList = serviceInput.getMenuIdList();

        RoleMenuRelatnInput relatnServiceInput = new RoleMenuRelatnInput();
        relatnServiceInput.setRoleId(roleId);
        // 根据roleId删除关联表中相关数据
        roleMenuRelatnService.delByRoleId(relatnServiceInput);
        // 菜单ID不为空时，插入新的角色菜单信息
        if (ObjectUtil.isNotEmpty(menuIdList)) {
            relatnServiceInput.setMenuIdList(menuIdList);
            logger.debug("relatnServiceInput:{}", relatnServiceInput);
            // 插入角色菜单关联表
            roleMenuRelatnService.listAdd(relatnServiceInput);
        }
        logger.debug("RoleServiceImpl.roleMdf end:<<<<<<<<<<<<<<<<<");
    }

    /**
     * 新增角色
     * @param serviceInput
     */
    @Override
    public void roleAdd(RoleInput serviceInput) {
        logger.debug("RoleServiceImpl.roleAdd begin >>>>>>>>>>>>>>>>>>>");
        long snowFlakeId = IdUtil.getSnowflakeNextId();
        String roleId = String.format("r%s", snowFlakeId);
        logger.debug("角色ID:{}", roleId);
        Role role = new Role();
        BeanUtils.copyProperties(serviceInput, role);
        role.setRoleId(roleId);
        save(role);
        List<String> menuIdList = serviceInput.getMenuIdList();
        // 菜单ID不为空时，插入角色菜单关联表
        if (ObjectUtil.isNotEmpty(menuIdList)) {
            RoleMenuRelatnInput relatnServiceInput = new RoleMenuRelatnInput();
            relatnServiceInput.setRoleId(roleId);
            relatnServiceInput.setMenuIdList(menuIdList);
            // 插入角色菜单关联表
            logger.debug("relatnServiceInput:{}", relatnServiceInput);
            roleMenuRelatnService.listAdd(relatnServiceInput);
        }
        logger.debug("RoleServiceImpl.roleAdd end:<<<<<<<<<<<<<<<<<");
    }

    /**
     * 删除角色信息
     * @param serviceInput
     */
    @Override
    public void roleDel(RoleInput serviceInput) {
        logger.debug("RoleServiceImpl.roleDel begin >>>>>>>>>>>>>>>>>>>");
        String roleId = serviceInput.getRoleId();
        Role role = getById(roleId);
        if (ObjectUtil.isEmpty(role)) {
            logger.error("查询无记录");
            throw new TransException(ResultCodeEnum.TRAN100701);
        }
        RecdStatUtil.recdStatChk(role.getRecdStat());
        // 将记录状态修改为1-已删除
        role.setRecdStat(TransConsts.RECD_STAT_1);
        updateById(role, true);
        RoleMenuRelatnInput relatnServiceInput = new RoleMenuRelatnInput();
        relatnServiceInput.setRoleId(roleId);
        // 根据roleId删除关联表中相关数据
        roleMenuRelatnService.delByRoleId(relatnServiceInput);
        logger.debug("RoleServiceImpl.roleDel end:<<<<<<<<<<<<<<<<<");
    }
}
