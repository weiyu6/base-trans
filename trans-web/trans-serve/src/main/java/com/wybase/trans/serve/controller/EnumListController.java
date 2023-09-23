package com.wybase.trans.serve.controller;

import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.Result;
import com.wybase.trans.base.result.ResultCodeEnum;
import com.wybase.trans.serve.model.entity.generate.EnumList;
import com.wybase.trans.serve.model.vo.EnumListVo;
import com.wybase.trans.serve.service.IEnumListService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举列表
 * @author weiyu
 * @date 2023/9/23
 */
@RestController
@RequestMapping("/online/enumlist")
public class EnumListController {
    private static final Logger logger = LoggerFactory.getLogger(EnumListController.class);

    @Autowired
    private IEnumListService enumListService;

    /**
     * 根据枚举id查询枚举值
     */
    @ApiOperation(value = "根据枚举id查询枚举值")
    @PostMapping("/enumByEnumId")
    public Result enumByEnumId(@RequestBody EnumListVo vo) {
        logger.debug("EnumListController.enumByKid begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("request:{}", vo);
        String enumId = vo.getEnumId();
        if (StringUtils.isEmpty(enumId)) {
            throw new TransException(ResultCodeEnum.NULL_ERROR, "enumId不能为空");
        }
        List<EnumList> enumListList = enumListService.enumByEnumId(enumId);
        logger.debug("EnumListController.enumByKid end:<<<<<<<<<<<<<<<<<");
        return Result.ok(enumListList);
    }

    /**
     * 批量查询枚举值
     */
    @ApiOperation(value = "批量查询枚举值")
    @PostMapping("/enumListQry")
    public Result enumListQry(@RequestBody EnumListVo request) {
        logger.debug("EnumListController.enumListQry begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("request:{}", request);
        List<EnumListVo.BatchEnumId> enumIds = request.getEnumIds();
        Map<String, List<EnumList>> map = new ConcurrentHashMap<>();
        for (EnumListVo.BatchEnumId batchEnumId : enumIds) {
            String enumId = batchEnumId.getEnumId();
            List<EnumList> enumListList = enumListService.enumByEnumId(enumId);
            map.put(enumId, enumListList);
        }
        logger.debug("EnumListController.enumListQry end:<<<<<<<<<<<<<<<<<");
        return Result.ok(map);
    }
}
