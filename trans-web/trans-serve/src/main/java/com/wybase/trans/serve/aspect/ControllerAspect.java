package com.wybase.trans.serve.aspect;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.ResultCodeEnum;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.common.consts.TransHeardConsts;
import com.wybase.trans.serve.config.TransContext;
import com.wybase.trans.serve.model.entity.generate.TransRecord;
import com.wybase.trans.serve.service.ITransRecordService;
import com.wybase.trans.serve.util.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 交易切面，使用切面在交易前、交易后进行相关处理
 * @author weiyu
 * @date 2023/7/29
 */
@Component
@Aspect
@Order(0) // 通过@Order注解设置切面的运行优先级，数值越小越先执行
public class ControllerAspect {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);
    @Autowired
    private ITransRecordService transRecordService;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 交易前处理，校验请求流水、登记交易流水信息等操作
     */
    @Before(TransConsts.AOP_POINTCUT_EXPRESSION)
    public void before(JoinPoint point) {
        logger.info("ControllerAspect.before begin >>>>>>>>>");
        try {
            Signature signature = point.getSignature();
            // 交易id
            String method = signature.getName();
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String ipAddr = IPUtils.getIpAddr(request) == null ? "" : IPUtils.getIpAddr(request);// 请求ip地址
            String url = request.getRequestURI() == null ? "" : request.getRequestURI();// 请求url地址
            String type = request.getMethod() == null ? "" : request.getMethod();// 请求方式
            Map<String, String> map = IPUtils.getOsAndBrowserInfo(request);
            // 操作系统
            String os = map.get("OS") == null ? "" : map.get("OS");
            // 浏览器
            String browser = map.get("BROWSER") == null ? "" : map.get("BROWSER");
            String chnl = request.getHeader("Chnl") == null ? "" : request.getHeader("Chnl");
            LocalDateTime dateTime = LocalDateTime.now();
            String transDate = dateTime.toLocalDate().toString();
            long snowFlakeId = IdUtil.getSnowflakeNextId();
            String transRecdId = String.format("%s%020d", transDate, snowFlakeId).replace("-", "");
            logger.info("交易流水号：{}", transRecdId);

            TransRecord transRecord = new TransRecord();
            transRecord.setTransRecdId(transRecdId);
            transRecord.setUserId(TransContext.getString(TransHeardConsts.TOKEN_USER_ID));
            transRecord.setUserName(TransContext.getString(TransHeardConsts.TOKEN_USER_NAME));
            transRecord.setReqDate(dateTime);
            transRecord.setChnl(chnl);
            transRecord.setMethod(method);
            transRecord.setIpAddr(ipAddr);
            transRecord.setUrl(url);
            transRecord.setOs(os);
            transRecord.setBrowser(browser);
            transRecord.setTransStatus(TransConsts.TRANS_STATUS_2);
            transRecord.setRecdStat(TransConsts.RECD_STAT_0);
            logger.info("transRecord:{}", transRecord);

            threadPoolTaskExecutor.execute(() -> {
                logger.info("异步登记交易流水初始化信息");
                transRecordService.save(transRecord);
            });

            TransContext.setField(TransHeardConsts.START_DATE_TIME, dateTime);
            TransContext.setField(TransHeardConsts.TRANS_RECORD, transRecord);
            TransContext.setField(TransHeardConsts.TRANS_RECD_NUM, transRecdId);
            TransContext.setField(TransHeardConsts.IP_ADDR, ipAddr);
            TransContext.setField(TransHeardConsts.URL, url);
            TransContext.setField(TransHeardConsts.TYPE, type);
            TransContext.setField(TransHeardConsts.OS, os);
            TransContext.setField(TransHeardConsts.BROWSER, browser);
            TransContext.setField(TransHeardConsts.CHNL, chnl);
        } catch (Exception e) {
            logger.info("解析失败：", e);
            throw new TransException(ResultCodeEnum.FAIL, "参数解析失败！");
        }
        logger.info("ControllerAspect.end begin >>>>>>>>>");
    }

    /**
     * 交易成功处理，更新交易流水状态为交易成功
     */
    @AfterReturning(TransConsts.AOP_POINTCUT_EXPRESSION)
    public void afterReturning() {
        logger.info("ControllerAspect.afterReturning");
        // 获取当前时间设为此交易结束时间
        LocalDateTime endDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = (LocalDateTime) TransContext.getField(TransHeardConsts.START_DATE_TIME);
        Duration between = LocalDateTimeUtil.between(startDateTime, endDateTime);
        Long consumTime = between.toMillis();
        logger.info("交易开始时间:{}，交易结束时间:{}，交易耗时:{}", startDateTime, endDateTime, consumTime);
        TransRecord transRecord = (TransRecord) TransContext.getField(TransHeardConsts.TRANS_RECORD);
        if (ObjectUtil.isNotEmpty(transRecord)) {
            transRecord.setConsumTime(consumTime.intValue());
            transRecord.setErrorCode(ResultCodeEnum.SUCCESS.getCode());
            transRecord.setErrorMsg(ResultCodeEnum.SUCCESS.getMsg());
            transRecord.setTransStatus(TransConsts.TRANS_STATUS_1);
            threadPoolTaskExecutor.execute(() -> {
                logger.info("异步更新交易流水状态");
                transRecordService.updateById(transRecord, true);
            });
        }
        TransContext.init();
    }

    /**
     * 交易失败处理，更新交易流水状态为交易失败
     */
    @AfterThrowing(value = TransConsts.AOP_POINTCUT_EXPRESSION, throwing = "e")
    public void afterThrowing(Throwable e) {
        logger.info("ControllerAspect.afterThrowing");
        LocalDateTime endDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = (LocalDateTime) TransContext.getField(TransHeardConsts.START_DATE_TIME);
        Duration between = LocalDateTimeUtil.between(startDateTime, endDateTime);
        Long consumTime = between.toMillis();
        logger.info("交易开始时间:{}，交易结束时间:{}，交易耗时:{}", startDateTime, endDateTime, consumTime);
        TransRecord transRecord = (TransRecord) TransContext.getField(TransHeardConsts.TRANS_RECORD);
        if (ObjectUtil.isNotEmpty(transRecord)) {
            transRecord.setConsumTime(consumTime.intValue());
            transRecord.setErrorCode(((TransException) e).getErrorCode());
            transRecord.setErrorMsg(((TransException) e).getErrorMsg());
            transRecord.setTransStatus(TransConsts.TRANS_STATUS_0);
            threadPoolTaskExecutor.execute(() -> {
                logger.info("异步更新交易流水状态");
                transRecordService.updateById(transRecord, true);
            });
        }
        TransContext.init();
    }
}
