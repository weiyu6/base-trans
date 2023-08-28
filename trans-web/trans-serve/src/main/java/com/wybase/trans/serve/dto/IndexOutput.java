package com.wybase.trans.serve.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author weiyu
 * @date 2022/5/19
 */
@Data
@ToString
@NoArgsConstructor
public class IndexOutput{

    /**
     * 用户总数
     */
    private long userCount;
    /**
     * 博客总数
     */
    private long blogCount;
    /**
     * 总评论数
     */
    private long commentCount;
}
