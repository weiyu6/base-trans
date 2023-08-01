package com.wybase.trans.serve.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.serve.entity.generate.UserEntity;
import com.wybase.trans.serve.mapper.generate.UserMapper;
import com.wybase.trans.serve.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * 用户表 服务层实现。
 *
 * @author weiyu
 * @since 2023-08-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService {

}
