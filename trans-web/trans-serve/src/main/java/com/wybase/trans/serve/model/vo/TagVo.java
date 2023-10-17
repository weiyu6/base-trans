package com.wybase.trans.serve.model.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @author weiyu
 * @date 2023/10/17
 */
@Data
@ToString
public class TagVo {
    /**
     * 标签id
     */
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
     * 页码
     */
    private int pageNum = 1;

    /**
     * 每页显示数量
     */
    private int pageSize = 10;
}
