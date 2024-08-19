package com.wybase.trans.serve.model.entity.generate;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.handler.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
@Table(value = "b_cron_job_config")
public class CronJobConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务id
     */
    @Id
    private String jobId;

    /**
     * 任务名称
     */
    private String jobName;

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
    private String jobStat;

    /**
     * 任务是否允许并发：0-允许，1-不允许
     */
    private String concurrent;

    /**
     * 任务执行失败策略：1-放弃执行，2-执行一次，3-全部执行
     */
    private String misfirePolicy;

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
    private Integer expressionCount;

    /**
     * 执行间隔时间（秒）
     */
    private Integer expressionInterval;

    /**
     * 任务类型
     */
    private String jobType;

    /**
     * 任务关联的id集合
     */
    @Column(typeHandler = JacksonTypeHandler.class)
    private List<String> jobRelaIds;

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
