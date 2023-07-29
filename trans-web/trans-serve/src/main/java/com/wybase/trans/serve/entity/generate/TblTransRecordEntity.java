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
 * 交易流水表 实体类。
 *
 * @author weiyu
 * @since 2023-07-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "b_trans_record")
public class TblTransRecordEntity implements Serializable {
    /**
     * 交易流水号
     */
    @Id
    private String transRecdId;
    /**
     * 请求流水号
     */
    private String reqRecdId;
    /**
     * 全局流水号
     */
    private String globRecdId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 交易时间
     */
    private LocalDateTime transDate;
    /**
     * 请求方时间
     */
    private LocalDateTime reqDate;
    /**
     * 渠道号
     */
    private String chnl;
    /**
     * 渠道来源
     */
    private String chnlSrc;
    /**
     * 浏览器
     */
    private String browser;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 请求方法名
     */
    private String method;
    /**
     * ip地址
     */
    private String ipAddr;
    /**
     * mac地址
     */
    private String macAddr;
    /**
     * ip地址来源
     */
    private String ipSrc;
    /**
     * 请求url地址
     */
    private String url;
    /**
     * 请求方式
     */
    private String reqType;
    /**
     * 交易状态 0-交易失败,1-交易成功,2-交易处理中
     */
    private String transStatus;
    /**
     * 交易耗时
     */
    private Integer consumTime;
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
