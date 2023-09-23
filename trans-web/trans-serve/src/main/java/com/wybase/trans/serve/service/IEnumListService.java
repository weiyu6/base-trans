package com.wybase.trans.serve.service;

import com.mybatisflex.core.service.IService;
import com.wybase.trans.serve.model.entity.generate.EnumList;

import java.util.List;

/**
 * 枚举列表 服务层。
 *
 * @author weiyu
 * @since 2023-08-05
 */
public interface IEnumListService extends IService<EnumList> {

    List<EnumList> enumByEnumId(String enumId);
}
