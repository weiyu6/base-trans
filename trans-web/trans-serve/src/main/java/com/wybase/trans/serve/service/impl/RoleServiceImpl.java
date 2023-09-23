package com.wybase.trans.serve.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.serve.mapper.generate.RoleMapper;
import com.wybase.trans.serve.model.dto.RoleInput;
import com.wybase.trans.serve.model.dto.RoleOutput;
import com.wybase.trans.serve.model.entity.generate.Role;
import com.wybase.trans.serve.model.entity.generate.table.RoleTableDef;
import com.wybase.trans.serve.service.IRoleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色表 服务层实现。
 * @author weiyu
 * @since 2023-08-05
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

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
}
