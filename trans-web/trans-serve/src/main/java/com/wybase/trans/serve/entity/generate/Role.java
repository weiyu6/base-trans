package com.wybase.trans.serve.entity.generate;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色表 实体类。
 *
 * @author weiyu
 * @since 2023-08-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "b_role")
public class Role implements Serializable {
    /**
     * 角色ID
     */
    @Id
    private String roleId;
    /**
     * 角色名称
     */
    private String roleNm;
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 角色状态：0-正常，1-停用
     */
    private String roleStat;
    /**
     * 简介
     */
    private String summy;
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
