package com.wybase.trans.serve.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * @author weiyu
 * @date 2023/9/23
 */
@Data
@ToString
public class EnumListVo {

    private BigInteger id;
    /**
     * 枚举名称
     */
    private String remark;
    /**
     * 枚举ID
     */
    private String enumId;
    /**
     * 排序
     */
    private int seq;
    /**
     * 枚举键
     */
    private String keyNm;
    /**
     * 枚举值
     */
    private String keyId;

    /**
     * 枚举状态
     */
    private String enumStat;

    private List<BatchEnumId> enumIds;

    /**
     * 页码
     */
    private int pageNum;

    /**
     * 每页显示数量
     */
    private int pageSize;

    // TODO 内部类需要为静态，并且需要使用无参构造器
    @NoArgsConstructor
    @Data
    @ToString
    public static class BatchEnumId implements Serializable {
        private static final long serialVersionUID = -3432282727946240179L;
        private String enumId;
    }
}
