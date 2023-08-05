package com.wybase.trans.serve.entity.generate;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 枚举列表 实体类。
 *
 * @author weiyu
 * @since 2023-08-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "b_enum_list")
public class EnumList implements Serializable {
    /**
     * 枚举id
     */
    @Id
    private String enumId;
    /**
     * 序号
     */
    @Id
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
     * 备注
     */
    private String remark;
    /**
     * 记录状态：0-正常，1-删除
     */
    private String recdStat;

}
