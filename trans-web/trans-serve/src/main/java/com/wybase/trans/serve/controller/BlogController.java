package com.wybase.trans.serve.controller;

import com.wybase.trans.base.aspect.MethodName;
import com.wybase.trans.base.result.Result;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.serve.model.dto.BlogInput;
import com.wybase.trans.serve.model.dto.BlogOutput;
import com.wybase.trans.serve.model.vo.BlogVo;
import com.wybase.trans.serve.service.IBlogService;
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
 * 博客
 * @author weiyu
 * @date 2023/10/17
 */
@Api(value = "博客", tags = "博客")
@RestController
@RequestMapping("/online/blog")
public class BlogController {
    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);
    @Autowired
    private IBlogService blogService;
    @MethodName(value = "博客列表查询", transType = TransConsts.TRANS_TYPE_1)
    @PostMapping("/blogList")
    public Result blogList(@RequestBody BlogVo vo) {
        logger.debug("BlogController.blogList begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("vo:{}", vo);
        BlogInput input = new BlogInput();
        BeanUtils.copyProperties(vo, input);
        BlogOutput serviceOutput = blogService.blogList(input);
        logger.debug("BlogController.blogList end:<<<<<<<<<<<<<<<<<");
        return Result.ok(serviceOutput);
    }
}
