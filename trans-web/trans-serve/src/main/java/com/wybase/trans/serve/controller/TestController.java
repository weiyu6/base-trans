package com.wybase.trans.serve.controller;

import com.wybase.trans.base.aspect.MethodName;
import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.Result;
import com.wybase.trans.base.result.ResultCodeEnum;
import com.wybase.trans.serve.entity.generate.TblSysLogEntity;
import com.wybase.trans.serve.mapper.custom.TestMapper;
import com.wybase.trans.serve.service.SysLogService;
import com.wybase.trans.serve.vo.TestVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@RestController
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private TestMapper mapper;

    @Autowired
    private SysLogService sysLogService;

    @GetMapping("/test")
    public Result test(@RequestParam(required = false) String flag) {
        Map<String, String> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", "18");
        if ("0".equals(flag)) {
            throw new TransException(ResultCodeEnum.ERROR);
        }
        if ("1".equals(flag)) {
            throw new TransException(ResultCodeEnum.FAIL, "");
        }
        List<TblSysLogEntity> list = mapper.list();
        return Result.fail().data("info", list);
    }

    @MethodName(value = "测试")
    @PostMapping("/testpost")
    public Result test1(@RequestBody TestVo vo) {
        logger.info("vo:{}", vo);
        String username = vo.getUsername();
        String age = vo.getAge();
        TblSysLogEntity sysLog = new TblSysLogEntity();
        sysLog.setLogId("123");
        sysLog.setUserId("1");
        sysLog.setUserNm("1");
        sysLog.setChnl("1");
        sysLog.setIp("1");
        sysLog.setIpSrc("1");
        sysLog.setUrl("1");
        sysLog.setTransType("1");
        sysLog.setReqType("1");
        sysLog.setClassPath("1");
        sysLog.setMethod("1");
        sysLog.setMethodNm("1");
        sysLog.setParams("1");
        sysLog.setModuleId("1");
        sysLog.setOtherData("1");
        sysLog.setOs("1");
        sysLog.setBrowser("1");
        sysLog.setConsumTime(0);
        sysLog.setTransRecdNum("1");
        sysLog.setCreateTime(LocalDateTime.now());
        sysLog.setUpdateTime(LocalDateTime.now());
        sysLog.setRecdStat("1");
        sysLogService.save(sysLog);
        if ("18".equals(age)) {
            throw new TransException(ResultCodeEnum.FAIL, "失败测试");
        }
        return Result.ok().data("info", vo);
    }
}
