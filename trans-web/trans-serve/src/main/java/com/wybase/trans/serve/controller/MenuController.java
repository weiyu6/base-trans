package com.wybase.trans.serve.controller;

import com.wybase.trans.base.result.Result;
import com.wybase.trans.serve.dto.MenuInput;
import com.wybase.trans.serve.dto.MenuOutput;
import com.wybase.trans.serve.service.IMenuService;
import com.wybase.trans.serve.vo.MenuVo;
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
@RestController
@RequestMapping("/online/menu")
public class MenuController {
    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private IMenuService menuService;
    /**
     * 查询菜单树形列表
     * @param request
     */
    @PostMapping("/menutree")
    public Result menutree(@RequestBody(required = false) MenuVo request) {
        logger.debug("MenuController.menulist begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("request:{}", request);
        MenuInput serviceInput = new MenuInput();
        BeanUtils.copyProperties(request, serviceInput);
        MenuOutput output = menuService.menuListAll(serviceInput);
        logger.debug("MenuController.menulist end:<<<<<<<<<<<<<<<<<");
        return Result.ok().data("data", output);
    }
}
