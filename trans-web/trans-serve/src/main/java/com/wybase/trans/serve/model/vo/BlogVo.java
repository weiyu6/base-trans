package com.wybase.trans.serve.model.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author weiyu
 * @date 2023/10/17
 */
@Data
@ToString
public class BlogVo {
    /**
     * 博客id
     */
    private String blogId;

    /**
     * 发表博客用户id
     */
    private String userId;

    /**
     * 作者
     */
    private String author;

    /**
     * 博客标题
     */
    private String title;

    /**
     * 博客简介
     */
    private String summy;

    /**
     * 标签id
     */
    private String tagId;

    /**
     * 博客分类id
     */
    private String classfcId;

    /**
     * 博客点击数
     */
    private Integer clickCount;

    /**
     * 博客收藏数
     */
    private Integer collectCount;

    /**
     * 标题图片uid
     */
    private String fileUid;

    /**
     * 0-原创，1-转载，2-推广
     */
    private String blogType;

    /**
     * 文章出处
     */
    private String articlesPart;

    /**
     * 外链【如果是推广，那么将跳转到外链】
     */
    private String outsideLink;

    /**
     * 推荐等级(0:正常)
     */
    private String level;

    /**
     * 是否发布：0：否，1：是
     */
    private String publishFlg;

    /**
     * 排序字段
     */
    private Integer sort;

    /**
     * 是否开启评论(0:否 1:是)
     */
    private String openComment;

    /**
     * 文章来源【0 后台添加，1 用户投稿】
     */
    private String articleSrc;

    /**
     * 博客内容
     */
    private String content;

    /**
     * 标签,一篇博客对应多个标签
     */
    private List<String> tagIds;

    /**
     * 页码
     */
    private int pageNum;

    /**
     * 每页显示数量
     */
    private int pageSize;
}
