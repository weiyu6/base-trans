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
 * 标签表 实体类。
 *
 * @author weiyu
 * @since 2023-10-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "b_tag")
public class Tag implements Serializable {
    /**
     * 标签id
     */
    @Id
    private String tagId;
    /**
     * 标签名称
     */
    private String tagNm;
    /**
     * 标签内容
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
