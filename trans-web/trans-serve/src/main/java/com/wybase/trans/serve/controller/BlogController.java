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

    @MethodName(value = "添加博客", transType = TransConsts.TRANS_TYPE_0)
    @PostMapping("/blogAdd")
    public Result blogAdd(@RequestBody BlogVo vo) {
        logger.debug("BlogController.blogAdd begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("vo:{}", vo);
        BlogInput blogInput = new BlogInput();
        BeanUtils.copyProperties(vo, blogInput);
        blogService.blogAdd(blogInput);
        logger.debug("BlogController.blogAdd end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }

    @MethodName(value = "博客内容修改", transType = TransConsts.TRANS_TYPE_0)
    @PostMapping("/blogMdf")
    public Result blogMdf(@RequestBody BlogVo vo) {
        logger.debug("BlogController.blogMdf begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("vo:{}", vo);
        BlogInput blogInput = new BlogInput();
        BeanUtils.copyProperties(vo, blogInput);
        blogService.blogMdf(blogInput);
        logger.debug("BlogController.blogMdf end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }

    @MethodName(value = "博客删除", transType = TransConsts.TRANS_TYPE_0)
    @PostMapping("/blogDel")
    public Result blogDel(@RequestBody BlogVo vo) {
        logger.debug("BlogController.blogDel begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("vo:{}", vo);
        BlogInput blogInput = new BlogInput();
        BeanUtils.copyProperties(vo, blogInput);
        blogService.blogDel(blogInput);
        logger.debug("BlogController.blogDel end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }

}
