package com.wybase.trans.serve.controller;

import com.wybase.trans.base.aspect.MethodName;
import com.wybase.trans.base.result.Result;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.serve.model.dto.MenuInput;
import com.wybase.trans.serve.model.dto.MenuOutput;
import com.wybase.trans.serve.model.vo.MenuVo;
import com.wybase.trans.serve.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单
 * @author weiyu
 * @date 2023/8/28
 */
@Api(value = "菜单服务", tags = "菜单服务")
@RestController
@RequestMapping("/online/menu")
public class MenuController {
    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private IMenuService menuService;

    @MethodName(value = "查询菜单树形列表", transType = TransConsts.TRANS_TYPE_1)
    @ApiOperation(value = "查询菜单树形列表")
    @PostMapping("/menutree")
    public Result menutree(@RequestBody(required = false) MenuVo request) {
        logger.debug("MenuController.menulist begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("request:{}", request);
        MenuInput input = new MenuInput();
        BeanUtils.copyProperties(request, input);
        MenuOutput output = menuService.menuListAll(input);
        logger.debug("MenuController.menulist end:<<<<<<<<<<<<<<<<<");
        return Result.ok(output);
    }

    @MethodName(value = "查询按钮列表", transType = TransConsts.TRANS_TYPE_1)
    @ApiOperation(value = "查询按钮列表")
    @PostMapping("/buttonList")
    public Result buttonList(@RequestBody MenuVo request) {
        logger.debug("MenuController.buttonList begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("request:{}", request);
        MenuInput input = new MenuInput();
        BeanUtils.copyProperties(request, input);
        MenuOutput output = menuService.buttonList(input);
        logger.debug("MenuController.buttonList end:<<<<<<<<<<<<<<<<<");
        return Result.ok(output);
    }

    @MethodName(value = "根据菜单ID查询菜单信息", transType = TransConsts.TRANS_TYPE_1)
    @ApiOperation(value = "根据菜单ID查询菜单信息")
    @PostMapping("/menuInfoQry")
    public Result menuInfoQry(@RequestBody MenuVo request) {
        logger.debug("MenuController.menuInfoQry begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("request:{}", request);
        MenuInput input = new MenuInput();
        BeanUtils.copyProperties(request, input);
        MenuOutput output = menuService.menuInfoQry(input);
        logger.debug("MenuController.menuInfoQry end:<<<<<<<<<<<<<<<<<");
        return Result.ok(output);
    }

    @MethodName(value = "根据条件查询菜单列表", transType = TransConsts.TRANS_TYPE_1)
    @ApiOperation(value = "根据条件查询菜单列表")
    @PostMapping("/menuList")
    public Result menuList(@RequestBody MenuVo request) {
        logger.debug("MenuController.menuList begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("request:{}", request);
        MenuInput input = new MenuInput();
        BeanUtils.copyProperties(request, input);
        MenuOutput output = menuService.menuList(input);
        logger.debug("MenuController.menuList end:<<<<<<<<<<<<<<<<<");
        return Result.ok(output);
    }

    @MethodName(value = "菜单修改", transType = TransConsts.TRANS_TYPE_0)
    @ApiOperation(value = "菜单修改")
    @PostMapping("/menuMdf")
    public Result menuMdf(@RequestBody MenuVo request) {
        logger.debug("MenuController.menuMdf begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("request:{}", request);
        MenuInput input = new MenuInput();
        BeanUtils.copyProperties(request, input);
        menuService.menuMdf(input);
        logger.debug("MenuController.menuMdf end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }

    @MethodName(value = "按钮查询", transType = TransConsts.TRANS_TYPE_1)
    @ApiOperation(value = "按钮查询")
    @PostMapping("/buttonTree")
    public Result buttonTree(@RequestBody MenuVo request) {
        logger.debug("MenuController.buttonTree begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("request:{}", request);
        MenuInput serviceInput = new MenuInput();
        BeanUtils.copyProperties(request, serviceInput);
        MenuOutput output = menuService.buttonTree(serviceInput);
        logger.debug("MenuController.buttonTree end:<<<<<<<<<<<<<<<<<");
        return Result.ok(output);
    }

    @MethodName(value = "添加按钮", transType = TransConsts.TRANS_TYPE_0)
    @ApiOperation(value = "添加按钮")
    @PostMapping("/buttonAdd")
    public Result buttonAdd(@RequestBody MenuVo request) {
        logger.debug("MenuController.buttonAdd begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("request:{}", request);
        MenuInput serviceInput = new MenuInput();
        BeanUtils.copyProperties(request, serviceInput);
        menuService.buttonAdd(serviceInput);
        logger.debug("MenuController.buttonAdd end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }

    @MethodName(value = "修改按钮", transType = TransConsts.TRANS_TYPE_0)
    @ApiOperation(value = "修改按钮")
    @PostMapping("/buttonMdf")
    public Result buttonMdf(@RequestBody MenuVo request) {
        logger.debug("MenuController.buttonMdf begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("request:{}", request);
        MenuInput serviceInput = new MenuInput();
        BeanUtils.copyProperties(request, serviceInput);
        menuService.buttonMdf(serviceInput);
        logger.debug("MenuController.buttonMdf end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }

    @MethodName(value = "删除菜单", transType = TransConsts.TRANS_TYPE_0)
    @ApiOperation(value = "删除菜单")
    @PostMapping("/menuDel")
    public Result menuDel(@RequestBody MenuVo request) {
        logger.debug("MenuController.menuDel begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("request:{}", request);
        MenuInput serviceInput = new MenuInput();
        BeanUtils.copyProperties(request, serviceInput);
        menuService.menuDel(serviceInput);
        logger.debug("MenuController.menuDel end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }

    @MethodName(value = "查询菜单按钮列表", transType = TransConsts.TRANS_TYPE_1)
    @ApiOperation(value = "查询菜单按钮列表")
    @PostMapping("/menuButtonTree")
    public Result menuButtonTree(@RequestBody MenuVo vo) {
        logger.debug("MenuController.menuButtonTree begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("vo:{}", vo);
        MenuInput serviceInput = new MenuInput();
        BeanUtils.copyProperties(vo, serviceInput);
        MenuOutput output = menuService.menuButtonTree(serviceInput);
        logger.debug("MenuController.menuButtonTree end:<<<<<<<<<<<<<<<<<");
        return Result.ok(output);
    }
}
