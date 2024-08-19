package com.wybase.trans.serve.controller;

import com.wybase.trans.base.aspect.MethodName;
import com.wybase.trans.base.result.Result;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.serve.model.vo.QuartzJobVo;
import com.wybase.trans.serve.service.ICronJobConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private ICronJobConfigService cronJobConfigService;

    @MethodName(value = "新增定时任务", transType = TransConsts.TRANS_TYPE_0)
    @Operation(summary = "新增定时任务")
    @PostMapping("/addJob")
    public Result addJob(@RequestBody QuartzJobVo quartzJobVo) {
        logger.info("QuartzJobController.addJob begin >>>>>>>>>>>>>>>>>>>");
        logger.info("quartzJobVo:{}", quartzJobVo);
        cronJobConfigService.addJob(quartzJobVo);
        logger.debug("LoginController.login end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }
}
