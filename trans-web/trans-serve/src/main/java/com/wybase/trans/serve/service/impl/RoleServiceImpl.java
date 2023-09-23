package com.wybase.trans.serve.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.serve.model.entity.generate.Role;
import com.wybase.trans.serve.mapper.generate.RoleMapper;
import com.wybase.trans.serve.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * 角色表 服务层实现。
 *
 * @author weiyu
 * @since 2023-08-05
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
