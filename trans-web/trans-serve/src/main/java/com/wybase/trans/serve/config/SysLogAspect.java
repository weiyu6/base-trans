package com.wybase.trans.serve.config;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONObject;
import com.wybase.trans.base.aspect.MethodName;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.common.consts.TransHeardConsts;
import com.wybase.trans.serve.model.entity.generate.SysLog;
import com.wybase.trans.serve.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 自定义日志
 * @author weiyu
 * @date 2023/10/15
 */
@Component
@Aspect
@Order(1)
public class SysLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(SysLogAspect.class);
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private ISysLogService sysLogService;

    @Around(value = "@annotation(methodName)")
    public Object around(ProceedingJoinPoint point, MethodName methodName) throws Throwable {
        logger.debug("SysLogAspect.around begin >>>>>>>>>>>>>>>>>>>");
        // 执行任务
        Object result = point.proceed();

        // 记录日志
        SysLog sysLog = new SysLog();
        logInfoInit(methodName, sysLog);
        String reqJson = JSONObject.toJSONString(point.getArgs());
        // 请求类路径
        String methodClas = point.getTarget().getClass().getName();
        // 请求方法
        String method = point.getSignature().getName();
        // 获取返回信息
        String resultparams = JSONObject.toJSONString(result);

        sysLog.setClassPath(methodClas);
        sysLog.setMethod(method);
        sysLog.setParams(reqJson);
        sysLog.setResultparams(resultparams);
        sysLog.setTransStatus(TransConsts.TRANS_STATUS_1);
        logger.info("sysLogSucc:{}", sysLog);
        // 记录日志标志为true时，进行日志记录
        if (methodName.save()) {
            threadPoolTaskExecutor.execute(() -> sysLogService.save(sysLog));
        }
        logger.debug("SysLogAspect.around end:<<<<<<<<<<<<<<<<<");
        return result;
    }

    /**
     * 错误日志
     * @param point
     * @param methodName
     * @param e
     */
    @AfterThrowing(value = "@annotation(methodName)", throwing = "e")
    public void AfterThrowing(JoinPoint point, MethodName methodName, Throwable e) {
        // 记录日志
        SysLog sysLog = new SysLog();
        logInfoInit(methodName, sysLog);
        String reqJson = JSONObject.toJSONString(point.getArgs());
        // 请求类路径
        String methodClas = point.getTarget().getClass().getName();
        // 请求方法
        String method = point.getSignature().getName();

        String errormsg = e.toString() + " " + Arrays.toString(e.getStackTrace());
        sysLog.setClassPath(methodClas);
        sysLog.setMethod(method);
        sysLog.setParams(reqJson);
        sysLog.setErrormsg(errormsg);
        sysLog.setTransStatus(TransConsts.TRANS_STATUS_0);
        logger.info("sysLogFail:{}", sysLog);
        // 记录日志标志为true时，进行日志记录
        if (methodName.save()) {
            threadPoolTaskExecutor.execute(() -> sysLogService.save(sysLog));
        }

    }

    /**
     * 日志信息初始化
     * @param methodName
     * @param sysLog
     */
    private void logInfoInit(MethodName methodName, SysLog sysLog) {
        logger.debug("SysLogAspect.saveLog begin >>>>>>>>>>>>>>>>>>>");
        String userId = TransContext.getString(TransHeardConsts.TOKEN_USER_ID);
        String userName = TransContext.getString(TransHeardConsts.TOKEN_USER_NAME);
        String chnl = TransContext.getString(TransHeardConsts.CHNL);// 请求渠道
        String ipAddr = TransContext.getString(TransHeardConsts.IP_ADDR);// IP地址
        String url = TransContext.getString(TransHeardConsts.URL);// 请求url
        String type = TransContext.getString(TransHeardConsts.TYPE);// 请求方式
        String os = TransContext.getString(TransHeardConsts.OS);// 操作系统
        String browser = TransContext.getString(TransHeardConsts.BROWSER);// 浏览器
        String transRecdNum = TransContext.getString(TransHeardConsts.TRANS_RECD_NUM);// 交易流水号

        // 请求方法中文描述
        String methodNm = methodName.value();

        // 日志ID：交易日期+雪花算法
        long snowFlakeId = IdUtil.getSnowflakeNextId();
        String transDate = LocalDateTime.now().toLocalDate().toString().replace("-", "");
        String logId = String.format("l%s%020d", transDate, snowFlakeId);

        sysLog.setLogId(logId);
        sysLog.setUserId(userId);
        sysLog.setUserNm(userName);
        sysLog.setChnl(chnl);
        sysLog.setIp(ipAddr);
        sysLog.setUrl(url);
        sysLog.setIpSrc("");
        sysLog.setTransType(methodName.transType());// 交易类型
        sysLog.setReqType(type);
        sysLog.setMethodNm(methodNm);
        sysLog.setModuleId("");
        sysLog.setOtherData("");
        sysLog.setOs(os);
        sysLog.setBrowser(browser);
        sysLog.setTransRecdId(transRecdNum);
        sysLog.setRecdStat(TransConsts.RECD_STAT_0);
    }
}
