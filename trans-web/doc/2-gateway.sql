use `trans`;

create table if not exists b_gateway_route
(
    id          bigint unsigned primary key auto_increment,
    route_id    varchar(255) not null comment '路由id',
    uri         varchar(255) not null comment '路由地址',
    uri_type    varchar(4)   not null default '1' comment '路由地址类型：1-lb模式(默认)，2-http模式',
    sort        int          not null default 100 comment '排序字段，越小越靠前',
    content     varchar(255) comment '路由简介',
    route_stat  varchar(2)            default '0' comment '路由状态：0-启用，1-禁用',
    create_time datetime     not null default current_timestamp comment '创建时间',
    update_time datetime     not null default current_timestamp on update current_timestamp(0) comment '更新时间',
    recd_stat   varchar(2)            default '0' comment '记录状态：0-正常，1-删除',
    constraint uk_gr_route_id unique (route_id)
) engine = innodb
  default charset = utf8mb4 comment = '网关路由信息表';

create table if not exists b_gateway_route_param
(
    id               bigint unsigned primary key auto_increment,
    route_id         varchar(255) not null comment '路由id',
    param_value      varchar(255) not null comment '参数value',
    param_type       varchar(4)   not null comment '参数类型：1-predicate，2-filter',
    content          varchar(255) comment '路由参数简介',
    route_param_stat varchar(2)            default '0' comment '路由参数状态：0-启用，1-禁用',
    create_time      datetime     not null default current_timestamp comment '创建时间',
    update_time      datetime     not null default current_timestamp on update current_timestamp(0) comment '更新时间',
    recd_stat        varchar(2)            default '0' comment '记录状态：0-正常，1-删除',
    index idx_grp_route_id (route_id)
) engine = innodb
  default charset = utf8mb4 comment = '网关路由参数表';