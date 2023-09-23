package com.wybase.trans.serve.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.serve.model.entity.generate.EnumList;
import com.wybase.trans.serve.mapper.generate.EnumListMapper;
import com.wybase.trans.serve.service.IEnumListService;
import org.springframework.stereotype.Service;

/**
 * 枚举列表 服务层实现。
 *
 * @author weiyu
 * @since 2023-08-05
 */
@Service
public class EnumListServiceImpl extends ServiceImpl<EnumListMapper, EnumList> implements IEnumListService {

}
