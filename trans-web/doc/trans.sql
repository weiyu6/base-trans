create database if not exists trans
    default character set utf8mb4
    default collate utf8mb4_general_ci;

use `trans`;

create table if not exists b_trans_record
(
    trans_recd_id varchar(32)  not null comment '交易流水号',
    req_recd_id   varchar(32)  null     default null comment '请求流水号',
    glob_recd_id  varchar(32)  null     default null comment '全局流水号',
    user_name     varchar(256) null     default null comment '用户名',
    user_id       varchar(32)  null     default null comment '用户id',
    trans_date    datetime              default null comment '交易时间',
    req_date      datetime              default null comment '请求方时间',
    chnl          varchar(16)  null     default null comment '渠道号',
    chnl_src      varchar(16)  null     default null comment '渠道来源',
    browser       varchar(256)          default null comment '浏览器',
    os            varchar(256)          default null comment '操作系统',
    method        varchar(32)  null     default null comment '请求方法名',
    ip_addr       varchar(32)  null     default null comment 'ip地址',
    mac_addr      varchar(32)  null     default null comment 'mac地址',
    ip_src        varchar(32)  null     default null comment 'ip地址来源',
    url           varchar(64)  null     default null comment '请求url地址',
    req_type      varchar(32)  null     default null comment '请求方式',
    trans_status  varchar(2)   null     default null comment '交易状态 0-交易失败,1-交易成功,2-交易处理中',
    consum_time   int(11)               default '0' comment '交易耗时',
    create_time   datetime     not null default current_timestamp comment '创建时间',
    update_time   datetime     not null default current_timestamp on update current_timestamp(0) comment '更新时间',
    recd_stat     varchar(2)   not null default '0' comment '记录状态：0-正常，1-删除',
    primary key (trans_recd_id) using btree,
    index idx_trans_reqserialid_channel (req_recd_id, chnl) using btree
) engine = innodb
  default charset = utf8mb4 comment = '交易流水表';

create table if not exists b_sys_log
(
    log_id         varchar(32)  not null comment '日志ID',
    user_id        varchar(32)  null comment '用户ID',
    user_nm        varchar(128) null comment '用户名',
    chnl           varchar(16)  null comment '渠道',
    ip             varchar(64)  null comment 'IP地址',
    ip_src         varchar(128) null comment 'IP地址来源',
    url            varchar(64)  null     default null comment '请求url地址',
    trans_type     varchar(8)   not null comment '交易类型(0-操作类，1-查询类)',
    req_type       varchar(16)  null comment '请求方式',
    class_path     varchar(256) null comment '请求类路径',
    method         varchar(32)  null comment '请求方法',
    method_nm      varchar(128) null comment '请求方法中文名',
    params         longtext comment '请求参数',
    module_id      varchar(128) null comment '模块id（文章id，标签id，分类id）',
    other_data     varchar(256) null comment '附加数据(比如搜索内容)',
    os             varchar(64)  null comment '操作系统',
    browser        varchar(64)  null comment '浏览器',
    consum_time    int comment '交易耗时',
    trans_recd_num varchar(36)  not null comment '交易流水号',
    create_time    datetime     not null default current_timestamp comment '创建时间',
    update_time    datetime     not null default current_timestamp on update current_timestamp(0) comment '更新时间',
    recd_stat      varchar(2)   not null default '0' comment '记录状态：0-正常，1-删除',
    primary key (log_id)
) engine = innodb
  default charset = utf8mb4 comment = '日志表';