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
 * 角色菜单关联表 实体类。
 *
 * @author weiyu
 * @since 2023-08-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "b_role_menu_relatn")
public class RoleMenuRelatn implements Serializable {
    /**
     * 角色菜单ID
     */
    @Id
    private String rmrId;
    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 菜单ID
     */
    private String menuId;
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
