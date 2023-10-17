package com.wybase.trans.common.consts;

/**
 * 系统常量
 * @author weiyu
 * @date 2023/7/29
 */
public class TransConsts {
    private TransConsts(){

    }

    /**
     * 切点路径
     */
    public static final String AOP_POINTCUT_EXPRESSION = "execution(* com.wybase.trans.serve.controller.*.*.*(..)) || execution(* com.wybase.trans.serve.controller.*.*(..))";
    /**
     * 记录状态：0-正常、1-已删除
     */
    public static final String RECD_STAT_0 = "0";
    /**
     * 记录状态：0-正常、1-已删除
     */
    public static final String RECD_STAT_1 = "1";
    /**
     * 交易状态码：0-交易失败、1-交易成功、2-交易处理中
     */
    public static final String TRANS_STATUS_0 = "0";
    /**
     * 交易状态码：0-交易失败、1-交易成功、2-交易处理中
     */
    public static final String TRANS_STATUS_1 = "1";
    /**
     * 交易状态码：0-交易失败、1-交易成功、2-交易处理中
     */
    public static final String TRANS_STATUS_2 = "2";
    /**
     * 角色ID:0-普通用户，1-管理员，2-超级管理员
     */
    public static final String ROLE_ID_0 = "0";
    /**
     * 角色ID:0-普通用户，1-管理员，2-超级管理员
     */
    public static final String ROLE_ID_1 = "1";
    /**
     * 角色ID:0-普通用户，1-管理员，2-超级管理员
     */
    public static final String ROLE_ID_2 = "2";
    /**
     * 菜单类型(1-菜单,2-按钮)
     */
    public static final String MENU_TYPE_1 = "1";
    /**
     * 菜单类型(1-菜单,2-按钮)
     */
    public static final String MENU_TYPE_2 = "2";
    /**
     * 菜单状态(0:禁止,1:正常)
     */
    public static final String MENU_STAT_0 = "0";
    /**
     * 菜单状态(0:禁止,1:正常)
     */
    public static final String MENU_STAT_1 = "1";
    /**
     * 菜单级别(1-一级菜单，2-二级菜单，3-三级菜单)
     */
    public static final String MENU_LVL_1 = "1";
    /**
     * 菜单级别(1-一级菜单，2-二级菜单，3-三级菜单)
     */
    public static final String MENU_LVL_2 = "2";
    /**
     * 菜单级别(1-一级菜单，2-二级菜单，3-三级菜单)
     */
    public static final String MENU_LVL_3 = "3";
    /**
     * 用户标签：0：普通用户，1：管理员，2：博主，3:超级管理员
     */
    public static final String USER_TAG_0 = "0";
    /**
     * 用户标签：0：普通用户，1：管理员，2：博主，3:超级管理员
     */
    public static final String USER_TAG_1 = "1";
    /**
     * 用户标签：0：普通用户，1：管理员，2：博主，3:超级管理员
     */
    public static final String USER_TAG_2 = "2";
    /**
     * 用户标签：0：普通用户，1：管理员，2：博主，3:超级管理员
     */
    public static final String USER_TAG_3 = "3";
    /**
     * 交易类型：0-操作类，1-查询类
     */
    public static final String TRANS_TYPE_0 = "0";
    /**
     * 交易类型：0-操作类，1-查询类
     */
    public static final String TRANS_TYPE_1 = "1";
}
