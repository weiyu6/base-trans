package com.wybase.trans.serve.service;

import com.mybatisflex.core.service.IService;
import com.wybase.trans.serve.model.dto.RoleInput;
import com.wybase.trans.serve.model.dto.RoleOutput;
import com.wybase.trans.serve.model.entity.generate.Role;

/**
 * 角色表 服务层。
 * @author weiyu
 * @since 2023-08-05
 */
public interface IRoleService extends IService<Role> {
    /**
     * 角色列表查询
     */
    RoleOutput roleList(RoleInput serviceInput);

    /**
     * 根据ID查询角色信息
     * @param serviceInput
     * @return
     */
    RoleOutput roleInfoQry(RoleInput serviceInput);

    /**
     * 角色信息修改
     * @param serviceInput
     */
    void roleMdf(RoleInput serviceInput);

    /**
     * 新增角色
     * @param serviceInput
     */
    void roleAdd(RoleInput serviceInput);

    /**
     * 删除角色信息
     * @param serviceInput
     */
    void roleDel(RoleInput serviceInput);
}
