package com.wybase.trans.serve.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.serve.mapper.generate.BlogMapper;
import com.wybase.trans.serve.model.dto.BlogInput;
import com.wybase.trans.serve.model.dto.BlogOutput;
import com.wybase.trans.serve.model.entity.custom.BlogExtend;
import com.wybase.trans.serve.model.entity.generate.Blog;
import com.wybase.trans.serve.model.entity.generate.table.BlogTableDef;
import com.wybase.trans.serve.service.IBlogService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 博客表 服务层实现。
 * @author weiyu
 * @since 2023-10-17
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {
    private static final Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);

    /**
     * 查询博客列表
     * @param input
     * @return
     */
    @Override
    public BlogOutput blogList(BlogInput input) {
        logger.debug("BlogServiceImpl.blogList begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("serviceInput:{}", input);
        BlogOutput serviceOutput = new BlogOutput();

        int pageNum = input.getPageNum();
        int pageSize = input.getPageSize();
        String title = input.getTitle();// 标题
        String classfcId = input.getClassfcId();// 分类ID
        String tagIdObj = input.getTagId();// 标签
        String openComment = input.getOpenComment();// 评论状态
        String publishFlg = input.getPublishFlg();// 发布状态
        String author = input.getAuthor();// 作者

        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(BlogTableDef.BLOG.TITLE.eq(title).when(StringUtils::isNotBlank))
                .and(BlogTableDef.BLOG.CLASSFC_ID.eq(classfcId).when(StringUtils::isNotBlank))
                .and(BlogTableDef.BLOG.TAG_ID.like(tagIdObj).when(StringUtils::isNotBlank))
                .and(BlogTableDef.BLOG.OPEN_COMMENT.like(openComment).when(StringUtils::isNotBlank))
                .and(BlogTableDef.BLOG.PUBLISH_FLG.like(publishFlg).when(StringUtils::isNotBlank))
                .and(BlogTableDef.BLOG.AUTHOR.like(author).when(StringUtils::isNotBlank))
                .and(BlogTableDef.BLOG.RECD_STAT.like(TransConsts.RECD_STAT_0))
                .orderBy(BlogTableDef.BLOG.CREATE_TIME.desc());
        Page<Blog> blogPage = page(new Page<>(pageNum, pageSize), queryWrapper);

        // 总笔数
        long total = blogPage.getTotalPage();
        // 数据转换
        List<BlogExtend> blogExtendList = new ArrayList<>();
        for (Blog blog : blogPage.getRecords()) {
            BlogExtend blogExtend = new BlogExtend();
            BeanUtils.copyProperties(blog, blogExtend);
            String tagId = blog.getTagId();
            String[] split = tagId.split("\\|");
            List<String> tagIds = Arrays.asList(split);
            blogExtend.setTagIds(tagIds);
            blogExtendList.add(blogExtend);
        }
        Page<BlogExtend> blogExtendPageInfo = new Page<>();
        blogExtendPageInfo.setTotalPage(total);
        blogExtendPageInfo.setRecords(blogExtendList);
        serviceOutput.setBlogExtendPageInfo(blogExtendPageInfo);
        logger.debug("BlogServiceImpl.blogList end:<<<<<<<<<<<<<<<<<");
        return serviceOutput;
    }
}
