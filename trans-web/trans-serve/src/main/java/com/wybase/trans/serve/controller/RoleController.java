package com.wybase.trans.serve.controller;

import com.wybase.trans.base.result.Result;
import com.wybase.trans.serve.model.dto.RoleInput;
import com.wybase.trans.serve.model.dto.RoleOutput;
import com.wybase.trans.serve.model.vo.RoleVo;
import com.wybase.trans.serve.service.IRoleService;
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
 * 角色管理
 * @author weiyu
 * @date 2023/9/23
 */
@Api(value = "角色服务", tags = "角色服务")
@RestController
@RequestMapping("/online/role")
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private IRoleService roleService;

    @ApiOperation(value = "角色列表查询")
    @PostMapping("/roleList")
    public Result roleList(@RequestBody RoleVo vo) {
        logger.debug("RoleController.roleList begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("request:{}", vo);
        RoleInput serviceInput = new RoleInput();
        BeanUtils.copyProperties(vo, serviceInput);
        RoleOutput serviceOutput = roleService.roleList(serviceInput);
        logger.debug("RoleController.roleList end:<<<<<<<<<<<<<<<<<");
        return Result.ok(serviceOutput);
    }

    @ApiOperation(value = "根据ID查询角色信息")
    @PostMapping("/roleInfoQry")
    public Result roleInfoQry(@RequestBody RoleVo vo) {
        logger.debug("RoleController.roleInfoQry begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("request:{}", vo);
        RoleInput serviceInput = new RoleInput();
        BeanUtils.copyProperties(vo, serviceInput);
        RoleOutput serviceOutput = roleService.roleInfoQry(serviceInput);
        logger.debug("RoleController.roleInfoQry end:<<<<<<<<<<<<<<<<<");
        return Result.ok(serviceOutput);
    }

    @ApiOperation(value = "角色信息修改")
    @PostMapping("/roleMdf")
    public Result roleMdf(@RequestBody RoleVo vo) {
        logger.debug("RoleController.roleMdf begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("vo:{}", vo);
        RoleInput serviceInput = new RoleInput();
        BeanUtils.copyProperties(vo, serviceInput);
        roleService.roleMdf(serviceInput);
        logger.debug("RoleController.roleMdf end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("/roleAdd")
    public Result roleAdd(@RequestBody RoleVo vo) {
        logger.debug("RoleController.roleAdd begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("request:{}", vo);
        RoleInput serviceInput = new RoleInput();
        BeanUtils.copyProperties(vo, serviceInput);
        roleService.roleAdd(serviceInput);
        logger.debug("RoleController.roleAdd end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }

    @ApiOperation(value = "删除角色")
    @PostMapping("/roleDel")
    public Result roleDel(@RequestBody RoleVo vo) {
        logger.debug("RoleController.roleDel begin:>>>>>>>>>>>>>>>>>>>");
        logger.debug("vo:{}", vo);
        RoleInput serviceInput = new RoleInput();
        BeanUtils.copyProperties(vo, serviceInput);
        roleService.roleDel(serviceInput);
        logger.debug("RoleController.roleDel end:<<<<<<<<<<<<<<<<<");
        return Result.ok();
    }
}
