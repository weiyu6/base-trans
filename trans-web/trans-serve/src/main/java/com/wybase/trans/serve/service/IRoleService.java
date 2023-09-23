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
}
