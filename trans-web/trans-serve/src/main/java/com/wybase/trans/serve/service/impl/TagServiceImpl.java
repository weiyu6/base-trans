package com.wybase.trans.serve.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.ResultCodeEnum;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.serve.mapper.generate.TagMapper;
import com.wybase.trans.serve.model.dto.TagInput;
import com.wybase.trans.serve.model.dto.TagOutput;
import com.wybase.trans.serve.model.entity.generate.Tag;
import com.wybase.trans.serve.model.entity.generate.table.TagTableDef;
import com.wybase.trans.serve.service.ITagService;
import com.wybase.trans.serve.util.RecdStatUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 标签表 服务层实现。
 * @author weiyu
 * @since 2023-10-17
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {
    private static final Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);

    /**
     * 查询标签列表
     * @param input
     * @return
     */
    @Override
    public TagOutput tagList(TagInput input) {
        logger.debug("TagServiceImpl.tagList begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("serviceInput：{}", input);
        int pageNum = input.getPageNum();
        int pageSize = input.getPageSize();
        String tagNm = input.getTagNm();

        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(TagTableDef.TAG.TAG_NM.like(tagNm).when(StringUtils::isNotBlank))
                .and(TagTableDef.TAG.RECD_STAT.eq(TransConsts.RECD_STAT_0))
                .orderBy(TagTableDef.TAG.SORT.asc());
        Page<Tag> tagList = page(new Page<>(pageNum, pageSize), queryWrapper);
        TagOutput serviceOutput = new TagOutput();
        serviceOutput.setTagPageInfo(tagList);
        logger.debug("TagServiceImpl.tagList end:<<<<<<<<<<<<<<<<<");
        return serviceOutput;
    }

    /**
     * 新增标签
     * @param input
     */
    @Override
    public void tagAdd(TagInput input) {
        logger.debug("TagServiceImpl.tagAdd begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("serviceInput:{}", input);
        long flakeId = IdUtil.getSnowflakeNextId();
        String tagId = String.format("t%s", flakeId);
        Tag tag = new Tag();
        BeanUtils.copyProperties(input, tag);
        tag.setTagId(tagId);
        save(tag);
        logger.debug("TagServiceImpl.tagAdd end:<<<<<<<<<<<<<<<<<");
    }

    /**
     * 标签修改
     * @param input
     */
    @Override
    public void tagMdf(TagInput input) {
        logger.debug("TagServiceImpl.tagMdf begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("serviceInput:{}", input);
        Tag tag = new Tag();
        BeanUtils.copyProperties(input, tag);
        updateById(tag);
        logger.debug("TagServiceImpl.tagMdf end:<<<<<<<<<<<<<<<<<");
    }

    /**
     * 删除标签
     * @param input
     */
    @Override
    public void tagDel(TagInput input) {
        logger.debug("TagServiceImpl.tagDel begin >>>>>>>>>>>>>>>>>>>");
        String tagId = input.getTagId();
        if (StringUtils.isEmpty(tagId)) {
            logger.error("标签ID不能为空");
            throw new TransException(ResultCodeEnum.NULL_ERROR, "标签ID不能为空");
        }
        Tag tag = getById(tagId);
        if (ObjectUtil.isEmpty(tag)) {
            logger.error("查询无记录");
            throw new TransException(ResultCodeEnum.TRAN100701);
        }
        RecdStatUtil.recdStatChk(tag.getRecdStat());
        tag.setRecdStat(TransConsts.RECD_STAT_1);
        updateById(tag);
        logger.debug("TagServiceImpl.tagDel end:<<<<<<<<<<<<<<<<<");
    }
}
