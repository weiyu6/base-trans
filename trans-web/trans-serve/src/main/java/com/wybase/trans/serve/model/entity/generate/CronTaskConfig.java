package com.wybase.trans.serve.model.entity.generate;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.mybatisflex.core.handler.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 定时任务配置表 实体类。
 *
 * @author weiyu
 * @since 2024-08-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "b_cron_task_config")
public class CronTaskConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务id
     */
    @Id
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 目标bean名称
     */
    private String beanTarget;

    /**
     * 目标bean中对应的方法
     */
    private String beanMethodTarget;

    /**
     * 任务状态：0-开启，1-关闭
     */
    private String taskStat;

    /**
     * 任务配置方式：0-simpl，1-cron表达式
     */
    private String cronType;

    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * 执行次数
     */
    private String expressionCount;

    /**
     * 执行间隔时间（秒）
     */
    private String expressionInterval;

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 任务关联的id集合
     */
    @Column(typeHandler = JacksonTypeHandler.class)
    private List<String> taskRelaIds;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新人
     */
    private String updateUser;

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
