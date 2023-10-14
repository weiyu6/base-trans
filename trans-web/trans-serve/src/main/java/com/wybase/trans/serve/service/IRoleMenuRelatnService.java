package com.wybase.trans.serve.service;

import com.mybatisflex.core.service.IService;
import com.wybase.trans.serve.model.dto.RoleMenuRelatnInput;
import com.wybase.trans.serve.model.entity.generate.RoleMenuRelatn;

import java.util.List;

/**
 * 角色菜单关联表 服务层。
 * @author weiyu
 * @since 2023-08-05
 */
public interface IRoleMenuRelatnService extends IService<RoleMenuRelatn> {
    /**
     * 根据角色id查询关联表列表
     * @param roleId
     */
    List<RoleMenuRelatn> roleMenuByRoleId(String roleId);

    /**
     * 根据roleId删除相关数据
     * @param relatnServiceInput
     */
    void delByRoleId(RoleMenuRelatnInput relatnServiceInput);

    /**
     * 插入角色与菜单对应关系
     * @param relatnServiceInput
     */
    void listAdd(RoleMenuRelatnInput relatnServiceInput);
}
