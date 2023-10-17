package com.wybase.trans.serve.controller;

import com.wybase.trans.base.aspect.MethodName;
import com.wybase.trans.base.result.Result;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.serve.model.dto.TagInput;
import com.wybase.trans.serve.model.dto.TagOutput;
import com.wybase.trans.serve.model.vo.TagVo;
import com.wybase.trans.serve.service.ITagService;
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
 * 标签
 * @author weiyu
 * @date 2023/10/17
 */
@Api(value = "博客标签", tags = "博客标签")
@RestController
@RequestMapping("/online/tag")
public class TagController {
    private static final Logger logger = LoggerFactory.getLogger(TagController.class);
    @Autowired
    private ITagService tagService;

    @MethodName(value = "查询标签列表", transType = TransConsts.TRANS_TYPE_1)
    @PostMapping("/tagList")
    public Result tagList(@RequestBody TagVo vo) {
        logger.debug("TagController.tagList begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("vo:{}", vo);
        TagInput serviceInput = new TagInput();
        BeanUtils.copyProperties(vo, serviceInput);
        TagOutput serviceOutput = tagService.tagList(serviceInput);
        logger.debug("TagController.tagList end:<<<<<<<<<<<<<<<<<");
        return Result.ok(serviceOutput);
    }

    @MethodName(value = "新增标签", transType = TransConsts.TRANS_TYPE_0)
    @PostMapping("/tagAdd")
    public Result tagAdd(@RequestBody TagVo vo) {
        logger.debug("TagController.tagAdd begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("vo:{}", vo);
        TagInput serviceInput = new TagInput();
        BeanUtils.copyProperties(vo, serviceInput);
        tagService.tagAdd(serviceInput);
        logger.debug("TagController.tagAdd end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }

    @MethodName(value = "标签修改", transType = TransConsts.TRANS_TYPE_0)
    @PostMapping("/tagMdf")
    public Result tagMdf(@RequestBody TagVo vo) {
        logger.debug("TagController.tagMdf begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("vo:{}", vo);
        TagInput serviceInput = new TagInput();
        BeanUtils.copyProperties(vo, serviceInput);
        tagService.tagMdf(serviceInput);
        logger.debug("TagController.tagMdf end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }

    @MethodName(value = "标签删除", transType = TransConsts.TRANS_TYPE_0)
    @PostMapping("/tagDel")
    public Result tagDel(@RequestBody TagVo vo) {
        logger.debug("TagController.tagDel begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("vo:{}", vo);
        TagInput serviceInput = new TagInput();
        BeanUtils.copyProperties(vo, serviceInput);
        tagService.tagDel(serviceInput);
        logger.debug("TagController.tagDel end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }
}
