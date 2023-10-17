package com.wybase.trans.serve.controller;

import com.wybase.trans.base.aspect.MethodName;
import com.wybase.trans.base.result.Result;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.serve.model.dto.ClassfcInput;
import com.wybase.trans.serve.model.dto.ClassfcOutput;
import com.wybase.trans.serve.model.vo.ClassfcVo;
import com.wybase.trans.serve.service.IClassfcService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 博客分类
 * @author weiyu
 * @date 2023/10/17
 */
@Api(value = "博客分类", tags = "博客分类")
@RestController
@RequestMapping("/online/classfc")
public class ClassfcController {
    private static final Logger log = LoggerFactory.getLogger(ClassfcController.class);
    @Autowired
    private IClassfcService classfcService;

    @MethodName(value = "分类列表查询", transType = TransConsts.TRANS_TYPE_1)
    @PostMapping("/classfcList")
    public Result classfcList(@RequestBody ClassfcVo vo) {
        log.debug("ClassfcController.classfcList begin:>>>>>>>>>>>>>>>>>>>");
        log.debug("vo:{}", vo);
        ClassfcInput serviceInput = new ClassfcInput();
        BeanUtils.copyProperties(vo, serviceInput);
        ClassfcOutput serviceOutput = classfcService.classfcList(serviceInput);
        log.debug("ClassfcController.classfcList end:<<<<<<<<<<<<<<<<<");
        return Result.ok(serviceOutput);
    }

    @MethodName(value = "新增分类", transType = TransConsts.TRANS_TYPE_0)
    @PostMapping("/classfcAdd")
    public Result classfcAdd(@RequestBody ClassfcVo vo) {
        log.debug("ClassfcController.classfcAdd begin:>>>>>>>>>>>>>>>>>>>");
        log.debug("vo:{}", vo);
        ClassfcInput serviceInput = new ClassfcInput();
        BeanUtils.copyProperties(vo, serviceInput);
        classfcService.classfcAdd(serviceInput);
        log.debug("ClassfcController.classfcAdd end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }

    @MethodName(value = "分类信息修改", transType = TransConsts.TRANS_TYPE_0)
    @PostMapping("/classfcMdf")
    public Result classfcMdf(@RequestBody ClassfcVo vo) {
        log.debug("ClassfcController.classfcMdf begin:>>>>>>>>>>>>>>>>>>>");
        log.debug("vo:{}", vo);
        ClassfcInput serviceInput = new ClassfcInput();
        BeanUtils.copyProperties(vo, serviceInput);
        classfcService.classfcMdf(serviceInput);
        log.debug("ClassfcController.classfcMdf end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }

    @MethodName(value = "分类信息删除", transType = TransConsts.TRANS_TYPE_0)
    @PostMapping("/classfcDel")
    public Result classfcDel(@RequestBody ClassfcVo vo) {
        log.debug("ClassfcController.classfcDel begin:>>>>>>>>>>>>>>>>>>>");
        log.debug("vo:{}", vo);
        ClassfcInput serviceInput = new ClassfcInput();
        BeanUtils.copyProperties(vo, serviceInput);
        classfcService.classfcDel(serviceInput);
        log.debug("ClassfcController.classfcDel end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }
}
