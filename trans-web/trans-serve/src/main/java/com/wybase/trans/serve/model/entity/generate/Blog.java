package com.wybase.trans.serve.model.entity.generate;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 博客表 实体类。
 *
 * @author weiyu
 * @since 2023-10-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "b_blog")
public class Blog implements Serializable {
    /**
     * 博客id
     */
    @Id
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
     * 交易流水号
     */
    private String transRecdNum;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 记录状态：0-正常，1-删除
     */
    private String recdStat;

}
