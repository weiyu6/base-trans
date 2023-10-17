package com.wybase.trans.serve.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.ResultCodeEnum;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.serve.mapper.generate.ClassfcMapper;
import com.wybase.trans.serve.model.dto.ClassfcInput;
import com.wybase.trans.serve.model.dto.ClassfcOutput;
import com.wybase.trans.serve.model.entity.generate.Classfc;
import com.wybase.trans.serve.model.entity.generate.table.ClassfcTableDef;
import com.wybase.trans.serve.service.IClassfcService;
import com.wybase.trans.serve.util.RecdStatUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 博客分类表 服务层实现。
 * @author weiyu
 * @since 2023-10-17
 */
@Service
public class ClassfcServiceImpl extends ServiceImpl<ClassfcMapper, Classfc> implements IClassfcService {
    private static final Logger logger = LoggerFactory.getLogger(ClassfcServiceImpl.class);

    /**
     * 分类列表查询
     * @param serviceInput
     * @return
     */
    @Override
    public ClassfcOutput classfcList(ClassfcInput serviceInput) {
        logger.debug("ClassfcServiceImpl.classfcList begin >>>>>>>>>>>>>>>>>>>");
        int pageNum = serviceInput.getPageNum();
        int pageSize = serviceInput.getPageSize();
        String classfcNm = serviceInput.getClassfcNm(); // 分类名称

        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(ClassfcTableDef.CLASSFC.CLASSFC_NM.like(classfcNm).when(StringUtils::isNotBlank))
                .and(ClassfcTableDef.CLASSFC.RECD_STAT.eq(TransConsts.RECD_STAT_0))
                .orderBy(ClassfcTableDef.CLASSFC.SORT.asc());
        Page<Classfc> pageList = page(new Page<>(pageNum, pageSize), queryWrapper);
        ClassfcOutput serviceOutput = new ClassfcOutput();
        serviceOutput.setClassfcPageInfo(pageList);
        logger.debug("ClassfcServiceImpl.classfcList end:<<<<<<<<<<<<<<<<<");
        return serviceOutput;
    }

    /**
     * 新增分类
     * @param serviceInput
     */
    @Override
    public void classfcAdd(ClassfcInput serviceInput) {
        logger.debug("ClassfcServiceImpl.classfcAdd begin >>>>>>>>>>>>>>>>>>>");
        long flakeId = IdUtil.getSnowflakeNextId();
        String classfcId = String.format("f%s", flakeId);
        Classfc classfc = new Classfc();
        BeanUtils.copyProperties(serviceInput, classfc);
        classfc.setClassfcId(classfcId);
        save(classfc);
        logger.debug("ClassfcServiceImpl.classfcAdd end:<<<<<<<<<<<<<<<<<");
    }

    /**
     * 分类信息修改
     * @param serviceInput
     */
    @Override
    public void classfcMdf(ClassfcInput serviceInput) {
        logger.debug("ClassfcServiceImpl.classfcMdf begin >>>>>>>>>>>>>>>>>>>");
        Classfc classfc = new Classfc();
        BeanUtils.copyProperties(serviceInput, classfc);
        updateById(classfc, true);
        logger.debug("ClassfcServiceImpl.classfcMdf end:<<<<<<<<<<<<<<<<<");
    }

    /**
     * 分类删除
     * @param serviceInput
     */
    @Override
    public void classfcDel(ClassfcInput serviceInput) {
        logger.debug("ClassfcServiceImpl.classfcDel begin >>>>>>>>>>>>>>>>>>>");
        String classfcId = serviceInput.getClassfcId();
        if (StringUtils.isEmpty(classfcId)) {
            logger.error("分类ID不能为空");
            throw new TransException(ResultCodeEnum.NULL_ERROR, "分类ID不能为空");
        }
        Classfc classfc = getById(classfcId);
        if (ObjectUtil.isEmpty(classfc)) {
            logger.error("查询无记录");
            throw new TransException(ResultCodeEnum.TRAN100701);
        }
        RecdStatUtil.recdStatChk(classfc.getRecdStat());
        classfc.setRecdStat(TransConsts.RECD_STAT_1);
        updateById(classfc, true);
        logger.debug("ClassfcServiceImpl.classfcDel end:<<<<<<<<<<<<<<<<<");
    }
}
