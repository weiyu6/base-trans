package com.wybase.trans.serve.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.ResultCodeEnum;
import com.wybase.trans.common.consts.BlogConsts;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.common.consts.TransHeardConsts;
import com.wybase.trans.serve.config.TransContext;
import com.wybase.trans.serve.mapper.generate.BlogMapper;
import com.wybase.trans.serve.model.dto.BlogInput;
import com.wybase.trans.serve.model.dto.BlogOutput;
import com.wybase.trans.serve.model.entity.custom.BlogExtend;
import com.wybase.trans.serve.model.entity.generate.Blog;
import com.wybase.trans.serve.model.entity.generate.UserInfo;
import com.wybase.trans.serve.model.entity.generate.table.BlogTableDef;
import com.wybase.trans.serve.service.IBlogService;
import com.wybase.trans.serve.service.IUserInfoService;
import com.wybase.trans.serve.util.RecdStatUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IUserInfoService userInfoService;

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

    /**
     * 添加博客
     * @param blogInput
     */
    @Override
    public void blogAdd(BlogInput blogInput) {
        logger.debug("BlogServiceImpl.blogAdd begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("blogInput:{}", blogInput);
        String userId = TransContext.getString(TransHeardConsts.TOKEN_USER_ID);
        if (StringUtils.isEmpty(userId)) {
            logger.error("用户ID不能为空");
            throw new TransException(ResultCodeEnum.NULL_ERROR, "用户ID不能为空");
        }
        // 根据userID查询出用户名称，作为作者
        UserInfo user = userInfoService.getById(userId);
        String userNm = user.getUserNm();
        String fileUid = blogInput.getFileUid();
        if (StringUtils.isEmpty(fileUid)) {
            // 封面为空时，使用默认封面，
            // TODO 后续将新建参数表，参数从数据库查询
            fileUid = "https://wylearn-file.oss-cn-qingdao.aliyuncs.com/logo/favicon.jpeg";
        }
        // 生成博客ID
        long flakeId = IdUtil.getSnowflakeNextId();
        String blogId = String.format("b%s", flakeId);
        logger.debug("博客ID:{}", blogId);
        List<String> tagIds = blogInput.getTagIds();

        // 将标签ID进行拼接
        StringBuffer buffer = new StringBuffer();
        for (String tagId : tagIds) {
            buffer.append(tagId).append("|");
        }
        String tagId = buffer.toString();
        logger.debug("博客的标签ID:{}", tagId);

        Blog blog = new Blog();
        BeanUtils.copyProperties(blogInput, blog);
        blog.setBlogId(blogId);
        blog.setUserId(userId);
        blog.setAuthor(userNm);
        blog.setTagId(tagId);
        blog.setFileUid(fileUid);
        blog.setArticleSrc(BlogConsts.ARTICLE_SRC_0);
        blog.setTransRecdNum(TransContext.getString(TransHeardConsts.TRANS_RECD_NUM));
        blog.setRecdStat(TransConsts.RECD_STAT_0);
        save(blog);
        logger.debug("BlogServiceImpl.blogAdd end:<<<<<<<<<<<<<<<<<");
    }

    /**
     * 博客内容修改
     * @param blogInput
     */
    @Override
    public void blogMdf(BlogInput blogInput) {
        logger.debug("BlogServiceImpl.blogMdf begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("serviceInput:{}", blogInput);
        String blogId = blogInput.getBlogId();
        if (StringUtils.isEmpty(blogId)) {
            logger.error("博客ID不能为空");
            throw new TransException(ResultCodeEnum.NULL_ERROR, "博客ID不能为空");
        }
        Blog blog = getById(blogId);
        if (ObjectUtil.isEmpty(blog)) {
            logger.error("查询无记录");
            throw new TransException(ResultCodeEnum.TRAN100701);
        }
        String fileUid = blogInput.getFileUid();
        if (StringUtils.isEmpty(fileUid)) {
            // 封面为空时，使用默认封面，
            // TODO 后续将新建参数表，参数从数据库查询
            fileUid = "https://wylearn-file.oss-cn-qingdao.aliyuncs.com/logo/favicon.jpeg";
        }
        RecdStatUtil.recdStatChk(blog.getRecdStat());
        List<String> tagIds = blogInput.getTagIds();
        // 将标签ID进行拼接
        StringBuffer buffer = new StringBuffer();
        for (String tagId : tagIds) {
            buffer.append(tagId).append("|");
        }
        String tagId = buffer.toString();
        logger.debug("博客的标签ID:{}", tagId);
        BeanUtils.copyProperties(blogInput, blog);
        blog.setTagId(tagId);
        blog.setFileUid(fileUid);
        logger.debug("blog:{}", blog);
        updateById(blog, true);
        logger.debug("BlogServiceImpl.blogMdf end:<<<<<<<<<<<<<<<<<");
    }

    /**
     * 博客删除
     * @param blogInput
     */
    @Override
    public void blogDel(BlogInput blogInput) {
        logger.debug("BlogServiceImpl.blogDel begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("serviceInput:{}", blogInput);
        String blogId = blogInput.getBlogId();
        if (StringUtils.isEmpty(blogId)) {
            logger.error("博客ID不能为空");
            throw new TransException(ResultCodeEnum.NULL_ERROR, "博客ID不能为空");
        }
        Blog blog = getById(blogId);
        if (ObjectUtil.isEmpty(blog)) {
            logger.error("查询无记录");
            throw new TransException(ResultCodeEnum.TRAN100701);
        }
        RecdStatUtil.recdStatChk(blog.getRecdStat());

        blog.setRecdStat(TransConsts.RECD_STAT_1);
        updateById(blog, true);
        logger.debug("BlogServiceImpl.blogDel end:<<<<<<<<<<<<<<<<<");
    }
}
