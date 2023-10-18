package com.wybase.trans.serve.service;

import com.mybatisflex.core.service.IService;
import com.wybase.trans.serve.model.dto.BlogInput;
import com.wybase.trans.serve.model.dto.BlogOutput;
import com.wybase.trans.serve.model.entity.generate.Blog;

/**
 * 博客表 服务层。
 * @author weiyu
 * @since 2023-10-17
 */
public interface IBlogService extends IService<Blog> {

    /**
     * 查询博客列表
     * @param input
     * @return
     */
    BlogOutput blogList(BlogInput input);

    /**
     * 添加博客
     * @param blogInput
     */
    void blogAdd(BlogInput blogInput);

    /**
     * 博客内容修改
     * @param blogInput
     */
    void blogMdf(BlogInput blogInput);

    /**
     * 博客删除
     * @param blogInput
     */
    void blogDel(BlogInput blogInput);
}
