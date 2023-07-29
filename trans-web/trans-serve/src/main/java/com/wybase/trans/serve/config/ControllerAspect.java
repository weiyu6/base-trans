package com.wybase.trans.serve.config;

import cn.hutool.core.util.IdUtil;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.serve.entity.generate.TblTransRecordEntity;
import com.wybase.trans.serve.service.TransRecordService;
import com.wybase.trans.serve.util.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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
    private TransRecordService transRecordService;

    /**
     * 交易前处理，校验请求流水、登记交易流水信息等操作
     * @param point
     */
    @Before(TransConsts.AOP_POINTCUT_EXPRESSION)
    public void before(JoinPoint point) {
        logger.info("ControllerAspect.before begin >>>>>>>>>");
        try {
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

            LocalDateTime dateTime = LocalDateTime.now();
            String transDate = dateTime.toLocalDate().toString();
            long snowFlakeId = IdUtil.getSnowflakeNextId();
            String transRecdId = String.format("%s%020d", transDate, snowFlakeId).replace("-", "");

            TblTransRecordEntity transRecord = new TblTransRecordEntity();
            transRecord.setTransRecdId(transRecdId);
            // transRecord.setReqRecdNum("");
            // transRecord.setGlobRecdNum("");
            // transRecord.setUserName("");
            transRecord.setUserId("tokenUserId");
            transRecord.setUserName("tokenUserName");
            transRecord.setTransDate(dateTime);
            transRecord.setReqDate(dateTime);
            transRecord.setChnl("chnl");
            // transRecord.setChnlSrc("");
            transRecord.setMethod("method");
            transRecord.setIpAddr(ipAddr);
            // transRecord.setMacAddr("");
            // transRecord.setIpSrc("");
            transRecord.setUrl(url);
            transRecord.setReqType(type);
            transRecord.setOs(os);
            transRecord.setBrowser(browser);
            transRecord.setTransStatus(TransConsts.TRANS_STATUS_2);
            transRecord.setRecdStat(TransConsts.RECD_STAT_0);
            logger.info("transRecord:{}", transRecord);
            boolean save = transRecordService.save(transRecord);

        } catch (Exception e) {
            logger.info("解析失败：", e);
        }

        logger.info("ControllerAspect.end begin >>>>>>>>>");
    }
}
