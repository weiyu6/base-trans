package com.wybase.trans.serve.model.entity.generate;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 枚举列表 实体类。
 *
 * @author weiyu
 * @since 2024-05-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "b_enum_list")
public class EnumList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private BigInteger id;

    /**
     * 枚举id
     */
    private String enumId;

    /**
     * 序号
     */
    private Integer seq;

    /**
     * 枚举key
     */
    private String keyId;

    /**
     * 枚举描述
     */
    private String keyNm;

    /**
     * 枚举状态：0-启用，1-禁用
     */
    private String enumStat;

    /**
     * 备注
     */
    private String remark;

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
