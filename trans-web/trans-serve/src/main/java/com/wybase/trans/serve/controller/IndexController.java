package com.wybase.trans.serve.controller;

import com.wybase.trans.base.result.Result;
import com.wybase.trans.serve.dto.IndexOutput;
import com.wybase.trans.serve.service.IIndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页相关
 * @author weiyu
 * @date 2023/8/28
 */
@RestController
@RequestMapping("/online/index")
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IIndexService indexService;

    @PostMapping("/init")
    public Result init() {
        logger.debug("IndexController.init begin:>>>>>>>>>>>>>>>>>>>");
        IndexOutput output = indexService.init();
        logger.debug("IndexController.init end:<<<<<<<<<<<<<<<<<");
        return Result.ok().data("data",output);
    }
}
