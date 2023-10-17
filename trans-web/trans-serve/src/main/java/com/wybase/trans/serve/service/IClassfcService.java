package com.wybase.trans.serve.service;

import com.mybatisflex.core.service.IService;
import com.wybase.trans.serve.model.dto.ClassfcInput;
import com.wybase.trans.serve.model.dto.ClassfcOutput;
import com.wybase.trans.serve.model.entity.generate.Classfc;

/**
 * 博客分类表 服务层。
 * @author weiyu
 * @since 2023-10-17
 */
public interface IClassfcService extends IService<Classfc> {
    /**
     * 分类列表查询
     * @param serviceInput
     * @return
     */
    ClassfcOutput classfcList(ClassfcInput serviceInput);

    /**
     * 新增分类
     * @param serviceInput
     */
    void classfcAdd(ClassfcInput serviceInput);

    /**
     * 分类信息修改
     * @param serviceInput
     */
    void classfcMdf(ClassfcInput serviceInput);

    /**
     * 分类删除
     * @param serviceInput
     */
    void classfcDel(ClassfcInput serviceInput);
}
