package com.wybase.trans.serve.entity.generate;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 日志表 实体类。
 *
 * @author weiyu
 * @since 2023-08-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "b_sys_log")
public class SysLog implements Serializable {
    /**
     * 日志ID
     */
    @Id
    private String logId;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名
     */
    private String userNm;
    /**
     * 渠道
     */
    private String chnl;
    /**
     * IP地址
     */
    private String ip;
    /**
     * IP地址来源
     */
    private String ipSrc;
    /**
     * 请求url地址
     */
    private String url;
    /**
     * 交易类型(0-操作类，1-查询类)
     */
    private String transType;
    /**
     * 请求方式
     */
    private String reqType;
    /**
     * 请求类路径
     */
    private String classPath;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 请求方法中文名
     */
    private String methodNm;
    /**
     * 请求参数
     */
    private String params;
    /**
     * 模块id（文章id，标签id，分类id）
     */
    private String moduleId;
    /**
     * 附加数据(比如搜索内容)
     */
    private String otherData;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 浏览器
     */
    private String browser;
    /**
     * 交易耗时
     */
    private Integer consumTime;
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
