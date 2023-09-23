package com.wybase.trans.serve.model.dto;

import com.wybase.trans.serve.model.entity.generate.Role;
import com.wybase.trans.serve.model.entity.generate.RoleMenuRelatn;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author weiyu
 * @date 2023/9/23
 */
@Data
@ToString
public class RoleOutput {
    /**
     * 角色列表
     */
    private List<Role> roleList;

    /**
     * 角色信息
     */
    private Role roleInfo;
    /**
     * 角色菜单关联集合
     */
    private List<RoleMenuRelatn> roleMenuRelatnList;
}
