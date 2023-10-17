package com.wybase.trans.serve.model.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author weiyu
 * @date 2023/10/17
 */
@Data
@ToString
public class ClassfcInput {
    /**
     * 分类id
     */
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
     * 页码
     */
    private int pageNum;

    /**
     * 每页显示数量
     */
    private int pageSize;
}
