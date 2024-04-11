create database if not exists trans
    default character set utf8mb4
    default collate utf8mb4_general_ci;

use `trans`;

create table if not exists b_trans_record
(
    trans_recd_id varchar(32)  not null comment '交易流水号',
    req_recd_id   varchar(32)  null     default null comment '请求流水号',
    user_name     varchar(256) null     default null comment '用户名',
    user_id       varchar(32)  null     default null comment '用户id',
    req_date      datetime              default null comment '请求方时间',
    chnl          varchar(16)  null     default null comment '渠道号',
    browser       varchar(256)          default null comment '浏览器',
    os            varchar(256)          default null comment '操作系统',
    method        varchar(32)  null     default null comment '请求方法名',
    ip_addr       varchar(32)  null     default null comment 'ip地址',
    mac_addr      varchar(32)  null     default null comment 'mac地址',
    ip_src        varchar(32)  null     default null comment 'ip地址来源',
    url           varchar(64)  null     default null comment '请求url地址',
    error_code    varchar(32)  null     default null comment '错误码',
    error_msg     varchar(256) null     default null comment '错误信息',
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
    log_id        varchar(32)  not null comment '日志ID',
    trans_recd_id varchar(36)  not null comment '交易流水号',
    user_id       varchar(32)  null comment '用户ID',
    user_nm       varchar(128) null comment '用户名',
    chnl          varchar(16)  null comment '渠道',
    ip            varchar(64)  null comment 'IP地址',
    ip_src        varchar(128) null comment 'IP地址来源',
    url           varchar(64)  null     default null comment '请求url地址',
    trans_type    varchar(8)   not null comment '交易类型(0-操作类，1-查询类)',
    req_type      varchar(16)  null comment '请求方式',
    class_path    varchar(256) null comment '请求类路径',
    method        varchar(32)  null comment '请求方法',
    method_nm     varchar(128) null comment '请求方法中文名',
    module_id     varchar(128) null comment '模块id（文章id，标签id，分类id）',
    other_data    varchar(256) null comment '附加数据(比如搜索内容)',
    os            varchar(64)  null comment '操作系统',
    browser       varchar(64)  null comment '浏览器',
    params        longtext comment '请求信息',
    resultParams  longtext comment '返回信息',
    errorMsg      longtext comment '错误消息',
    trans_status  varchar(2)   null     default '0' comment '交易状态 0-交易失败,1-交易成功,2-交易处理中',
    create_time   datetime     not null default current_timestamp comment '创建时间',
    update_time   datetime     not null default current_timestamp on update current_timestamp(0) comment '更新时间',
    recd_stat     varchar(2)   not null default '0' comment '记录状态：0-正常，1-删除',
    primary key (log_id)
) engine = innodb
  default charset = utf8mb4 comment = '日志表';

create table if not exists b_user_info
(
    user_id            varchar(32)  not null comment '用户id',
    user_nm            varchar(256) not null comment '用户名',
    pass_word          varchar(32)  not null comment '密码',
    nick_nm            varchar(256)          default null comment '昵称',
    gender             varchar(1)            default null comment '性别(1:男2:女)',
    avatar             varchar(128)          default null comment '个人头像',
    birthday           date                  default null comment '出生年月日',
    email              varchar(64)           default null comment '邮箱',
    valid_code         varchar(256)          default null comment '邮箱验证码',
    mobile             varchar(64)           default null comment '手机',
    qq_num             varchar(16)           default null comment 'qq号',
    wechat_num         varchar(64)           default null comment '微信号',
    summy              varchar(256)          default null comment '自我简介最多150字',
    login_count        int(11)               default 0 comment '登录次数',
    last_login_time    datetime              default null comment '最后登录时间',
    last_login_ip      varchar(64)           default null comment '最后登录ip',
    source             varchar(256)          default null comment '资料来源',
    uuid               varchar(256)          default null comment '平台uuid',
    profession         varchar(256)          default null comment '职业',
    comment_stat       varchar(1)   not null default 1 comment '评论状态 1:正常 0:禁言',
    ip_src             varchar(256)          default null comment 'ip来源',
    browser            varchar(256)          default null comment '浏览器',
    os                 varchar(256)          default null comment '操作系统',
    start_email_notice varchar(1)   not null default '0' comment '是否开启邮件通知 1:开启 0:关闭',
    user_tag           varchar(1)   not null default '0' comment '用户标签：0：普通用户，1：管理员，2：博主，3:超级管理员 等',
    role_id            varchar(256) not null comment '角色ID',
    loading_valid      varchar(1)   not null default '0' comment '是否通过加载校验【0 未通过，1 已通过】',
    trans_recd_num     varchar(36)  not null comment '交易流水号',
    create_time        datetime     not null default current_timestamp comment '创建时间',
    update_time        datetime     not null default current_timestamp on update current_timestamp(0) comment '更新时间',
    recd_stat          varchar(2)   not null default '0' comment '记录状态：0-正常，1-删除',
    primary key (user_id),
    unique index idx_user_info_username (user_nm)
) engine = innodb
  default charset = utf8mb4 comment ='用户信息表';

/*==============================================================*/
/* table: b_enum_list                                           */
/*==============================================================*/
create table if not exists b_enum_list
(
    enum_id   varchar(32) not null comment '枚举id',
    seq       int         not null comment '序号',
    key_id    varchar(256) comment '枚举key',
    key_nm    varchar(256) comment '枚举描述',
    remark    varchar(256) comment '备注',
    recd_stat varchar(2) comment '记录状态：0-正常，1-删除',
    primary key (enum_id, seq)
) engine = innodb
  default charset = utf8mb4 comment ='枚举列表';

/*==============================================================*/
/* Table: b_menu                                                */
/*==============================================================*/
create table if not exists b_menu
(
    menu_id          varchar(32) not null comment '菜单ID',
    high_lvl_id      varchar(32) not null comment '上级菜单ID',
    menu_lvl         varchar(2)  not null comment '菜单级别',
    menu_nm          varchar(32) comment '菜单名称',
    menu_type        varchar(2) comment '类型(1:菜单,2:按钮)',
    permission_value varchar(64) comment '权限值',
    path             varchar(128) comment '访问路径',
    component        varchar(128) comment '组件路径',
    icon             varchar(64) comment '图标',
    sort             int(11)              default 100 comment '排序字段，越小越靠前',
    menu_stat        varchar(2) comment '菜单状态(0:禁止,1:正常)',
    summy            varchar(128) comment '简介',
    link_flg         varchar(2)  not null comment '外部链接标志(0-否，1-是)',
    create_time      datetime             default CURRENT_TIMESTAMP comment '创建时间',
    update_time      datetime             default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    recd_stat        varchar(2)  not null default '0' comment '记录状态：0-正常，1-删除',
    primary key (menu_id),
    index idx_b_menu_highLvl_id (high_lvl_id) using btree
) engine = innodb
  default charset = utf8mb4 comment = '菜单表';

/*==============================================================*/
/* Table: t_role                                                */
/*==============================================================*/
create table if not exists b_role
(
    role_id     varchar(32) not null comment '角色ID',
    role_nm     varchar(64) comment '角色名称',
    role_code   varchar(32) comment '角色编码',
    role_stat   varchar(2) comment '角色状态：0-正常，1-停用',
    summy       varchar(128) comment '简介',
    create_time datetime             default CURRENT_TIMESTAMP comment '创建时间',
    update_time datetime             default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    recd_stat   varchar(2)  not null default '0' comment '记录状态：0-正常，1-删除',
    primary key (role_id)
) engine = innodb
  default charset = utf8mb4 comment = '角色表';

/*==============================================================*/
/* Table: b_role_menu                                           */
/*==============================================================*/
create table if not exists b_role_menu_relatn
(
    rmr_id      varchar(32) not null comment '角色菜单ID',
    role_id     varchar(32) not null comment '角色ID',
    menu_id     varchar(32) not null comment '菜单ID',
    create_time datetime             default CURRENT_TIMESTAMP comment '创建时间',
    update_time datetime             default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    recd_stat   varchar(2)  not null default '0' comment '记录状态：0-正常，1-删除',
    primary key (rmr_id),
    index idx_t_role_menu_roleid (role_id),
    index idx_t_role_menu_menuid (menu_id)
) engine = innodb
  default charset = utf8mb4 comment = '角色菜单关联表';

create table if not exists b_blog
(
    blog_id        varchar(32) not null comment '博客id',
    user_id        varchar(32)          default null comment '发表博客用户id',
    author         varchar(256)         default null comment '作者',
    title          varchar(256)         default null comment '博客标题',
    summy          varchar(256)         default null comment '博客简介',
    tag_id         varchar(128)          default null comment '标签id',
    classfc_id     varchar(128)          default null comment '博客分类id',
    click_count    int(11)              default 0 comment '博客点击数',
    collect_count  int(11)              default 0 comment '博客收藏数',
    file_uid       varchar(256)         default null comment '标题图片uid',
    blog_type      varchar(1)  not null default '0' comment '0-原创，1-转载，2-推广',
    articles_part  varchar(256)         default null comment '文章出处',
    outside_link   varchar(1024)        default null comment '外链【如果是推广，那么将跳转到外链】',
    level          varchar(1)           default '0' comment '推荐等级(0:正常)',
    publish_flg    varchar(1)           default '1' comment '是否发布：0：否，1：是',
    sort           int(11)     not null default 100 comment '排序字段',
    open_comment   varchar(1)  not null default 1 comment '是否开启评论(0:否 1:是)',
    article_src    varchar(1)  not null default '0' comment '文章来源【0 后台添加，1 用户投稿】',
    content        longtext comment '博客内容',
    trans_recd_num varchar(36) not null comment '交易流水号',
    create_time    datetime    not null default current_timestamp comment '创建时间',
    update_time    datetime    not null default current_timestamp on update current_timestamp(0) comment '更新时间',
    recd_stat      varchar(2)  not null default '0' comment '记录状态：0-正常，1-删除',
    primary key (blog_id),
    index idx_b_blog_tagid (tag_id) using btree,
    index idx_b_blog_classfcid (classfc_id) using btree
) engine = innodb
  default charset = utf8mb4 comment ='博客表';

create table if not exists b_classfc
(
    classfc_id  varchar(32) not null comment '分类id',
    classfc_nm  varchar(256)         default null comment '分类名称',
    content     varchar(256)         default null comment '分类简介',
    sort        int(11)              default 100 comment '排序字段，越小越靠前',
    click_count int(11)              default 0 comment '点击数',
    create_time datetime    not null default current_timestamp comment '创建时间',
    update_time datetime    not null default current_timestamp on update current_timestamp(0) comment '更新时间',
    recd_stat   varchar(2)  not null default '0' comment '记录状态：0-正常，1-删除',
    primary key (classfc_id)
) engine = innodb
  default charset = utf8mb4 comment ='博客分类表';

create table if not exists b_tag
(
    tag_id      varchar(32) not null comment '标签id',
    tag_nm      varchar(256) comment '标签名称',
    content     varchar(256)         default null comment '标签内容',
    sort        int(11)              default 100 comment '排序字段，越小越靠前',
    click_count int(11)              default 0 comment '点击数',
    create_time datetime    not null default current_timestamp comment '创建时间',
    update_time datetime    not null default current_timestamp on update current_timestamp(0) comment '更新时间',
    recd_stat   varchar(2)           default '0' comment '记录状态：0-正常，1-删除',
    primary key (tag_id)
) engine = innodb
  default charset = utf8mb4 comment ='标签表';