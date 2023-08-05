package com.wybase.trans.serve.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.serve.entity.generate.Menu;
import com.wybase.trans.serve.mapper.generate.MenuMapper;
import com.wybase.trans.serve.service.IMenuService;
import org.springframework.stereotype.Service;

/**
 * 菜单表 服务层实现。
 *
 * @author weiyu
 * @since 2023-08-05
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
