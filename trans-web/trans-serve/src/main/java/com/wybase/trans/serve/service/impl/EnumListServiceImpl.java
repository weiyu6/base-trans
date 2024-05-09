package com.wybase.trans.serve.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.ResultCodeEnum;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.serve.mapper.generate.EnumListMapper;
import com.wybase.trans.serve.model.dto.EnumOutput;
import com.wybase.trans.serve.model.entity.generate.EnumList;
import com.wybase.trans.serve.model.entity.generate.table.EnumListTableDef;
import com.wybase.trans.serve.model.vo.EnumListVo;
import com.wybase.trans.serve.service.IEnumListService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

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

    /**
     * 获取枚举值列表
     *
     * @param vo
     * @return
     */
    @Override
    public EnumOutput enumList(EnumListVo vo) {
        EnumOutput enumOutput = new EnumOutput();
        int pageNum = vo.getPageNum();
        int pageSize = vo.getPageSize();
        String remark = vo.getRemark();
        String enumId = vo.getEnumId();
        QueryWrapper queryWrapper = QueryWrapper.create()
                .from(EnumListTableDef.ENUM_LIST)
                .where(EnumListTableDef.ENUM_LIST.RECD_STAT.eq(TransConsts.RECD_STAT_0))
                .and(EnumListTableDef.ENUM_LIST.ENUM_ID.eq(enumId).when(StringUtils.isNotBlank(enumId)))
                .and(EnumListTableDef.ENUM_LIST.REMARK.like(remark).when(StringUtils.isNotBlank(remark)))
                .orderBy(EnumListTableDef.ENUM_LIST.ENUM_ID, true)
                .orderBy(EnumListTableDef.ENUM_LIST.SEQ, true);
        Page<EnumList> page = new Page<>(pageNum, pageSize);
        Page<EnumList> blogPage = page(page, queryWrapper);
        enumOutput.setEnumListPage(blogPage);
        return enumOutput;
    }

    /**
     * 新增枚举值
     *
     * @param vo
     */
    @Override
    public void enumAdd(EnumListVo vo) {
        String enumId = vo.getEnumId();
        int seq = vo.getSeq();
        if (StringUtils.isBlank(enumId)) {
            logger.error("枚举ID不能为空");
            throw new TransException(ResultCodeEnum.NULL_ERROR, "枚举ID不能为空");
        }
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(EnumListTableDef.ENUM_LIST.ENUM_ID.eq(enumId))
                .and(EnumListTableDef.ENUM_LIST.SEQ.eq(seq));
        EnumList enumInfo = getOne(queryWrapper);
        logger.info("根据enumID,seq查询到枚举信息，enumInfo:{}", enumInfo);
        if (ObjectUtil.isNotEmpty(enumInfo)) {
            if (StringUtils.equals(TransConsts.RECD_STAT_0, enumInfo.getRecdStat())) {
                logger.error("枚举值已存在");
                throw new TransException(ResultCodeEnum.ENUM_ALREADY_EXIST);
            }
            BigInteger id = enumInfo.getId();
            BeanUtils.copyProperties(vo, enumInfo);
            enumInfo.setId(id);
            enumInfo.setRecdStat(TransConsts.RECD_STAT_0);
            updateById(enumInfo, true);
        } else {
            enumInfo = new EnumList();
            BeanUtils.copyProperties(vo, enumInfo);
            enumInfo.setRecdStat(TransConsts.RECD_STAT_0);
            save(enumInfo);
        }
    }

    /**
     * 修改枚举值
     *
     * @param vo
     */
    @Override
    public void enumMdf(EnumListVo vo) {
        BigInteger id = vo.getId();
        String enumId = vo.getEnumId();
        int seq = vo.getSeq();
        if (StringUtils.isBlank(enumId)) {
            logger.error("枚举ID不能为空");
            throw new TransException(ResultCodeEnum.NULL_ERROR, "枚举ID不能为空");
        }
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(EnumListTableDef.ENUM_LIST.ENUM_ID.eq(enumId))
                .and(EnumListTableDef.ENUM_LIST.SEQ.eq(seq));
        EnumList enumInfo = getOne(queryWrapper);
        if (ObjectUtil.isNotEmpty(enumInfo)) {
            if (StringUtils.equals(TransConsts.RECD_STAT_0, enumInfo.getRecdStat())) {
                if (!Objects.equals(enumInfo.getId(), id)) {
                    logger.error("枚举值已存在");
                    throw new TransException(ResultCodeEnum.ENUM_ALREADY_EXIST);
                }
            } else {
                removeById(enumInfo);
            }

        }
        enumInfo = new EnumList();
        BeanUtils.copyProperties(vo, enumInfo);
        updateById(enumInfo, true);
    }

    @Override
    public void enumDel(EnumListVo vo) {
        BigInteger id = vo.getId();
        if (ObjectUtil.isEmpty(id)) {
            logger.error("ID不能为空");
            throw new TransException(ResultCodeEnum.NULL_ERROR, "ID不能为空");
        }
        EnumList enumInfo = getById(id);
        if (ObjectUtil.isEmpty(enumInfo)) {
            logger.error("字典不存在，id：{}", id);
            throw new TransException(ResultCodeEnum.ENUM_NOT_EXIST);
        }
        enumInfo.setRecdStat(TransConsts.RECD_STAT_1);
        updateById(enumInfo, true);
    }
}
