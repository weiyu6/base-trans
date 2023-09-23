package com.wybase.trans.serve.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author weiyu
 * @date 2023/9/23
 */
@Data
@ToString
public class EnumListVo {
    /**
     * 枚举ID
     */
    private String enumId;

    /**
     * 枚举key
     */
    private String keyId;

    private List<BatchEnumId> enumIds;

    // TODO 内部类需要为静态，并且需要使用无参构造器
    @NoArgsConstructor
    @Data
    @ToString
    public static class BatchEnumId implements Serializable {
        private static final long serialVersionUID = -3432282727946240179L;
        private String enumId;
    }
}
