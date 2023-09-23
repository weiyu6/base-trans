package com.wybase.trans.serve.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.serve.mapper.generate.EnumListMapper;
import com.wybase.trans.serve.model.entity.generate.EnumList;
import com.wybase.trans.serve.model.entity.generate.table.EnumListTableDef;
import com.wybase.trans.serve.service.IEnumListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 枚举列表 服务层实现。
 *
 * @author weiyu
 * @since 2023-08-05
 */
@Service
public class EnumListServiceImpl extends ServiceImpl<EnumListMapper, EnumList> implements IEnumListService {
private static final Logger logger = LoggerFactory.getLogger(EnumListServiceImpl.class);
    @Override
    public List<EnumList> enumByEnumId(String enumId) {
        logger.debug("enumByKid keyId:{}", enumId);

        QueryWrapper query = QueryWrapper.create();
        query.where(EnumListTableDef.ENUM_LIST.RECD_STAT.eq(TransConsts.RECD_STAT_0))
                .where(EnumListTableDef.ENUM_LIST.ENUM_ID.eq(enumId))
                .orderBy("seq");
        List<EnumList> enumLists = list(query);
        logger.debug("enumLists:{}", enumLists);
        return enumLists;
    }
}
