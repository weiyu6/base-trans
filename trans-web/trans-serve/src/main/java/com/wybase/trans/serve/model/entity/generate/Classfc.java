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
 * 博客分类表 实体类。
 *
 * @author weiyu
 * @since 2023-10-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "b_classfc")
public class Classfc implements Serializable {
    /**
     * 分类id
     */
    @Id
    private String classfcId;
    /**
     * 分类名称
     */
    private String classfcNm;
    /**
     * 分类简介
     */
    private String content;
    /**
     * 排序字段，越小越靠前
     */
    private Integer sort;
    /**
     * 点击数
     */
    private Integer clickCount;
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
