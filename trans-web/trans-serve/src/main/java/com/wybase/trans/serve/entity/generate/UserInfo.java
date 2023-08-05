package com.wybase.trans.serve.entity.generate;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息表 实体类。
 *
 * @author weiyu
 * @since 2023-08-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "b_user_info")
public class UserInfo implements Serializable {
    /**
     * 用户id
     */
    @Id
    private String userId;
    /**
     * 用户名
     */
    private String userNm;
    /**
     * 密码
     */
    private String passWord;
    /**
     * 昵称
     */
    private String nickNm;
    /**
     * 性别(1:男2:女)
     */
    private String gender;
    /**
     * 个人头像
     */
    private String avatar;
    /**
     * 出生年月日
     */
    private Date birthday;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 邮箱验证码
     */
    private String validCode;
    /**
     * 手机
     */
    private String mobile;
    /**
     * qq号
     */
    private String qqNum;
    /**
     * 微信号
     */
    private String wechatNum;
    /**
     * 自我简介最多150字
     */
    private String summy;
    /**
     * 登录次数
     */
    private Integer loginCount;
    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;
    /**
     * 最后登录ip
     */
    private String lastLoginIp;
    /**
     * 资料来源
     */
    private String source;
    /**
     * 平台uuid
     */
    private String uuid;
    /**
     * 职业
     */
    private String profession;
    /**
     * 评论状态 1:正常 0:禁言
     */
    private String commentStat;
    /**
     * ip来源
     */
    private String ipSrc;
    /**
     * 浏览器
     */
    private String browser;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 是否开启邮件通知 1:开启 0:关闭
     */
    private String startEmailNotice;
    /**
     * 用户标签：0：普通用户，1：管理员，2：博主，3:超级管理员 等
     */
    private String userTag;
    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 是否通过加载校验【0 未通过，1 已通过】
     */
    private String loadingValid;
    /**
     * 交易流水号
     */
    private String transRecdNum;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 记录状态：0-正常，1-删除
     */
    private String recdStat;

}
