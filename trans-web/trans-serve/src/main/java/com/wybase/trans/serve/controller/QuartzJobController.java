package com.wybase.trans.serve.controller;

import com.mybatisflex.core.paginate.Page;
import com.wybase.trans.base.aspect.MethodName;
import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.Result;
import com.wybase.trans.base.result.ResultCodeEnum;
import com.wybase.trans.common.consts.CronJobConsts;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.serve.model.entity.generate.CronJobConfig;
import com.wybase.trans.serve.model.vo.QuartzJobVo;
import com.wybase.trans.serve.service.ICronJobConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 定时任务
 * @author weiyu
 * @date 2024/8/19
 */
@Tag(name = "定时任务")
@RestController
@RequestMapping("/online/quartz")
public class QuartzJobController {
    private static final Logger logger = LoggerFactory.getLogger(QuartzJobController.class);
    @Resource
    private ICronJobConfigService service;

    @MethodName(value = "新增定时任务", transType = TransConsts.TRANS_TYPE_0)
    @Operation(summary = "新增定时任务")
    @PostMapping("/addJob")
    public Result addJob(@RequestBody QuartzJobVo quartzJobVo) {
        logger.info("QuartzJobController.addJob begin >>>>>>>>>>>>>>>>>>>");
        logger.info("quartzJobVo:{}", quartzJobVo);
        cronJobInfoChk(quartzJobVo);
        service.addJob(quartzJobVo);
        logger.debug("LoginController.login end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }

    @MethodName(value = "定时任务查询", transType = TransConsts.TRANS_TYPE_1)
    @Operation(summary = "定时任务查询")
    @PostMapping("/jobListQry")
    public Result jobListQry(@RequestBody QuartzJobVo quartzJobVo) {
        logger.info("QuartzJobController.jobListQry begin >>>>>>>>>>>>>>>>>>>");
        logger.info("quartzJobVo:{}", quartzJobVo);
        Page<CronJobConfig> blogPage = service.jobListQry(quartzJobVo);
        logger.debug("LoginController.login end:<<<<<<<<<<<<<<<<<");
        return Result.ok(blogPage);
    }

    private void cronJobInfoChk(QuartzJobVo quartzJobVo) {
        if (StringUtils.equals(CronJobConsts.JOB_STAT_ENABLE, quartzJobVo.getJobStat())
                && quartzJobVo.getEndTime().isBefore(LocalDateTime.now())) {
            logger.error("任务结束时间不能早于当前时间");
            throw new TransException(ResultCodeEnum.CRON_JOB_INFO_ERROR);
        }
        if (!CronExpression.isValidExpression(quartzJobVo.getCronExpression())) {
            logger.error("cron表达式无效");
            throw new TransException(ResultCodeEnum.CRON_JOB_INFO_ERROR, "cron表达式无效");
        }

    }
}
