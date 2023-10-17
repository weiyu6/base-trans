package com.wybase.trans.serve.service;

import com.mybatisflex.core.service.IService;
import com.wybase.trans.serve.model.dto.TagInput;
import com.wybase.trans.serve.model.dto.TagOutput;
import com.wybase.trans.serve.model.entity.generate.Tag;

/**
 * 标签表 服务层。
 *
 * @author weiyu
 * @since 2023-10-17
 */
public interface ITagService extends IService<Tag> {

    /**
     * 查询标签列表
     * @param input
     * @return
     */
    TagOutput tagList(TagInput input);

    /**
     * 新增标签
     * @param input
     */
    void tagAdd(TagInput input);

    /**
     * 标签修改
     * @param input
     */
    void tagMdf(TagInput input);

    /**
     * 删除标签
     * @param input
     */
    void tagDel(TagInput input);
}
