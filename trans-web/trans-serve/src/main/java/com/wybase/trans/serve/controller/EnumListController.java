package com.wybase.trans.serve.controller;

import com.alibaba.excel.EasyExcel;
import com.wybase.trans.base.aspect.MethodName;
import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.Result;
import com.wybase.trans.base.result.ResultCodeEnum;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.common.excel.DefaultExcelListener;
import com.wybase.trans.serve.model.dto.EnumOutput;
import com.wybase.trans.serve.model.entity.generate.EnumList;
import com.wybase.trans.serve.model.vo.Dict;
import com.wybase.trans.serve.model.vo.EnumListVo;
import com.wybase.trans.serve.service.IEnumListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举列表
 * @author weiyu
 * @date 2023/9/23
 */
@Tag(name = "枚举服务")
@RestController
@RequestMapping("/online/enumlist")
public class EnumListController {
    private static final Logger logger = LoggerFactory.getLogger(EnumListController.class);

    @Resource
    private IEnumListService enumListService;

    @MethodName(value = "根据枚举id查询枚举值", transType = TransConsts.TRANS_TYPE_1)
    @Operation(summary = "根据枚举id查询枚举值")
    @PostMapping("/enumByEnumId")
    public Result enumByEnumId(@RequestBody EnumListVo vo) {
        logger.debug("EnumListController.enumByKid begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("vo:{}", vo);
        String enumId = vo.getEnumId();
        if (StringUtils.isEmpty(enumId)) {
            throw new TransException(ResultCodeEnum.NULL_ERROR, "enumId不能为空");
        }
        List<EnumList> enumListList = enumListService.enumByEnumId(enumId);
        logger.debug("EnumListController.enumByKid end:<<<<<<<<<<<<<<<<<");
        return Result.ok(enumListList);
    }

    @MethodName(value = "批量查询枚举值", transType = TransConsts.TRANS_TYPE_1)
    @Operation(summary = "批量查询枚举值")
    @PostMapping("/enumListQry")
    public Result enumListQry(@RequestBody EnumListVo request) {
        logger.debug("EnumListController.enumListQry begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("vo:{}", request);
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

    @MethodName(value = "获取枚举值列表", transType = TransConsts.TRANS_TYPE_1)
    @Operation(summary = "获取枚举值列表")
    @PostMapping("/enumList")
    public Result enumList(@RequestBody EnumListVo vo) {
        logger.debug("EnumListController.enumList begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("vo:{}", vo);
        EnumOutput enumOutput = enumListService.enumList(vo);
        logger.debug("EnumListController.enumList end:<<<<<<<<<<<<<<<<<");
        return Result.ok(enumOutput.getEnumListPage());
    }

    @MethodName(value = "新增枚举值", transType = TransConsts.TRANS_TYPE_0)
    @Operation(summary = "新增枚举值")
    @PostMapping("/enumAdd")
    public Result enumAdd(@RequestBody EnumListVo vo) {
        logger.debug("EnumListController.enumAdd begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("vo:{}", vo);
        enumListService.enumAdd(vo);
        logger.debug("EnumListController.enumAdd end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }

    @MethodName(value = "修改枚举值", transType = TransConsts.TRANS_TYPE_0)
    @Operation(summary = "修改枚举值")
    @PostMapping("/enumMdf")
    public Result enumMdf(@RequestBody EnumListVo vo) {
        logger.debug("EnumListController.enumMdf begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("vo:{}", vo);
        enumListService.enumMdf(vo);
        logger.debug("EnumListController.enumMdf end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }

    @MethodName(value = "删除枚举值", transType = TransConsts.TRANS_TYPE_0)
    @Operation(summary = "删除枚举值")
    @PostMapping("/enumDel")
    public Result enumDel(@RequestBody EnumListVo vo) {
        logger.debug("EnumListController.enumDel begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("vo:{}", vo);
        enumListService.enumDel(vo);
        logger.debug("EnumListController.enumDel end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }

    @MethodName(value = "批量导入枚举值", transType = TransConsts.TRANS_TYPE_0, save = false)
    @Operation(summary = "批量导入枚举值")
    @PostMapping("/enumBatchImp")
    public Result enumBatchImp(@RequestPart("file") MultipartFile file) {
        logger.debug("EnumListController.enumBatchImp begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("file:{}", file);
        try {
            DefaultExcelListener listener = new DefaultExcelListener();
            EasyExcel.read(file.getInputStream(), Dict.class, listener).sheet().doRead();
            List<Dict> list = listener.getList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.debug("EnumListController.enumBatchImp end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }

}
