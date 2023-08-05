-- user 用户表
INSERT INTO `trans`.`b_user_info`(`user_id`, `user_nm`, `pass_word`, `nick_nm`, `gender`, `avatar`, `birthday`, `email`, `valid_code`, `mobile`, `qq_num`, `wechat_num`, `summy`, `login_count`, `last_login_time`, `last_login_ip`, `source`, `uuid`, `profession`, `comment_stat`, `ip_src`, `browser`, `os`, `start_email_notice`, `user_tag`, `role_id`, `loading_valid`, `trans_recd_num`, `create_time`, `update_time`, `recd_stat`)
VALUES ('u252015231142727680', 'admin', '27D0D9438E43F2A6F15C4792255B8D78', '赵子龙', '1', 'https://wylearn-file.oss-cn-qingdao.aliyuncs.com/2022-04-03/258230308216770560-logo-3.png', '1990-02-16', 'zzl@email.com', '', '15899996666', '123456', 'zzlnb', '吾乃常山赵子龙是也', 35, '2022-04-03 23:38:52', '127.0.0.1', 'meet', '', '3', '1', '', 'Chrome-99.0.4844.74', 'Windows', '1', '2', '2|', '1', '2022040300258230329150541824', '2022-03-17 20:02:31', '2022-04-03 23:38:52', '0');

-- b_enum_list枚举列表
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('GENDER', 1, '1', '男', '性别', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('GENDER', 2, '2', '女', '性别', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('COMMENT_STAT', 1, '0', '禁言', '评论状态', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('COMMENT_STAT', 2, '1', '正常', '评论状态', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('START_EMAIL_NOTICE', 1, '0', '关闭', '是否开启邮件通知', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('START_EMAIL_NOTICE', 2, '1', '开启', '是否开启邮件通知', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('USER_TAG', 1, '0', '普通用户', '用户标签', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('USER_TAG', 2, '1', '博主', '用户标签', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('USER_TAG', 3, '2', '管理员', '用户标签', '0');

insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('LOADING_VALID', 1, '0', '未通过', '是否通过加载校验', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('LOADING_VALID', 2, '1', '已通过', '是否通过加载校验', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('ORIGINAL_FLG', 1, '0', '否', '是否原创', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('ORIGINAL_FLG', 2, '1', '是', '是否原创', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('PUBLISH_FLG', 1, '0', '否', '是否发布', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('PUBLISH_FLG', 2, '1', '是', '是否发布', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('OPEN_COMMENT', 1, '0', '否', '是否开启评论', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('OPEN_COMMENT', 2, '1', '是', '是否开启评论', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('BLOG_TYPE', 1, '0', '博客', '文章类型', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('BLOG_TYPE', 2, '1', '推广', '文章类型', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('ARTICLE_SRC', 1, '0', '后台添加', '文章来源', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('ARTICLE_SRC', 2, '1', '用户投稿', '文章来源', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('CMNT_TYPE', 1, '0', '评论', '评论类型', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('CMNT_TYPE', 2, '1', '点赞', '评论类型', '0');

insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('PROFESSION', 1, '1', '党的机关、国家机关、群众团体和社会组织、企事业单位负责人', '职业', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('PROFESSION', 2, '2', '专业技术人员', '职业', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('PROFESSION', 3, '3', '办事人员和有关人员', '职业', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('PROFESSION', 4, '4', '社会生产服务和生活服务人员', '职业', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('PROFESSION', 5, '5', '农、林、牧、渔业生产及辅助人员', '职业', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('PROFESSION', 6, '6', '生产制造及有关人员', '职业', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('PROFESSION', 7, '7', '军人', '职业', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('PROFESSION', 8, '8', '其他', '职业', '0');

insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('TRANS_STATUS', 1, '0', '交易失败', '交易状态', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('TRANS_STATUS', 2, '1', '交易成功', '交易状态', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('TRANS_STATUS', 3, '2', '交易处理中', '交易状态', '0');

insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('TRANS_TYPE', 1, '0', '操作类', '交易类型', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('TRANS_TYPE', 2, '1', '查询类', '交易类型', '0');

insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('USER_ROLE', 1, '0', '普通用户', '用户标签', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('USER_ROLE', 2, '1', '管理员', '用户标签', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('USER_ROLE', 3, '2', '超级管理员', '用户标签', '0');

insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('MENU_LVL', 1, '1', '一级菜单', '菜单级别', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('MENU_LVL', 2, '2', '二级菜单', '菜单级别', '0');

insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('MENU_TYPE', 1, '1', '菜单', '菜单类型', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('MENU_TYPE', 2, '2', '按钮', '菜单类型', '0');

insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('BLOGTYPE', 1, '0', '原创', '博客类型', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('BLOGTYPE', 2, '1', '转载', '博客类型', '0');
insert into `trans`.`b_enum_list`(`enum_id`, `seq`, `key_id`, `key_nm`, `remark`, `recd_stat`)
values ('BLOGTYPE', 3, '2', '推广', '博客类型', '0');

-- b_menu菜单表
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m257143561118552064', '0', '1', '操作信息', '1', NULL, '/transinfo', 'Layout', 'el-icon-notebook-1', 4, '1', '日志交易明细等信息查询', '0', '2022-03-31 23:40:32', '2022-04-01 17:12:19', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m257147286235123712', 'm257143561118552064', '2', '交易记录', '1', NULL, 'transRecdList', '/meetblog/transinfo/transRecdList', 'el-icon-s-operation', 41, '1', '交易记录信息查询', '0', '2022-03-31 23:55:17', '2022-04-02 14:02:29', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m257147778239565824', 'm257143561118552064', '2', '日志列表', '1', NULL, 'syslogList', '/meetblog/transinfo/syslogList', 'el-icon-s-data', 40, '1', '日志信息查询', '0', '2022-03-31 23:57:14', '2022-04-02 14:02:24', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m257148294692605952', '0', '1', '权限管理', '1', NULL, '/auth', 'Layout', 'el-icon-s-check', 3, '1', '权限相关信息', '0', '2022-03-31 23:59:15', '2022-04-01 14:50:50', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m257148503338258432', 'm257148294692605952', '2', '菜单管理', '1', NULL, 'menuList', '/meetblog/auth/menuList', 'el-icon-menu', 34, '1', '菜单管理', '0', '2022-04-01 00:00:04', '2022-04-02 22:38:24', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m257148836525379584', '0', '1', '用户管理', '1', NULL, '/user', 'Layout', 'el-icon-user', 2, '1', '用户相关信息管理', '0', '2022-04-01 00:01:24', '2022-04-04 17:22:08', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m257149207754838016', 'm257148836525379584', '2', '用户列表', '1', NULL, 'userList', '/meetblog/user/userList', 'el-icon-user-solid', 21, '1', '用户信息管理', '0', '2022-04-01 00:02:52', '2022-04-04 17:22:04', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m257149844714426368', '0', '1', '博客管理', '1', NULL, '/blog', 'Layout', 'el-icon-folder-opened', 1, '1', '博客以及相关依赖信息管理', '0', '2022-04-01 00:05:24', '2022-04-02 14:01:15', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m257150080614666240', 'm257149844714426368', '2', '博客管理', '1', NULL, 'blog', '/meetblog/blog/blog', 'el-icon-s-order', 11, '1', '博客相关信息管理', '0', '2022-04-01 00:06:20', '2022-04-06 15:57:24', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m257150259094884352', 'm257149844714426368', '2', '标签管理', '1', NULL, 'tag', '/meetblog/blog/tag', 'el-icon-price-tag', 13, '1', '标签管理', '0', '2022-04-01 00:07:03', '2022-04-06 15:57:35', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m257371993995743232', '0', '1', '外链', '1', NULL, 'external-link', 'Layout', 'el-icon-link', 9, '1', '跳转到外部链接', '0', '2022-04-01 14:48:09', '2022-04-01 17:44:21', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m257372302008651776', 'm257371993995743232', '2', 'External Link', '1', NULL, 'https://panjiachen.gitee.io/vue-element-admin-site/zh/', NULL, 'el-icon-s-platform', 0, '1', 'vue-element-admin指南', '1', '2022-04-01 14:49:22', '2022-04-01 17:43:43', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m257373071566966784', 'm257371993995743232', '2', 'ElmentUI', '1', NULL, 'https://element.eleme.cn/2.13/#/zh-CN/component/installation', NULL, 'el-icon-postcard', 2, '1', 'ElmentUI指南', '1', '2022-04-01 14:52:26', '2022-04-01 22:39:54', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m257373465638604800', 'm257371993995743232', '2', '蘑菇博客后管', '1', NULL, 'http://demoadmin.moguit.cn/#/', NULL, 'el-icon-potato-strips', 3, '1', '蘑菇博客后管', '1', '2022-04-01 14:54:00', '2022-04-01 14:54:00', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m257665658706202624', 'm257148294692605952', '2', '按钮管理', '1', NULL, 'buttonList', '/meetblog/auth/buttonList', 'el-icon-switch-button', 35, '1', '按钮列表管理', '0', '2022-04-02 10:15:04', '2022-04-02 14:02:12', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m257776055220178944', 'm257665658706202624', '3', '添加', '2', NULL, '/buttonList/add', NULL, NULL, 2, '1', '按钮管理-添加', '0', '2022-04-02 17:34:15', '2022-05-20 14:13:09', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m257777338878529536', 'm257665658706202624', '3', '修改', '2', NULL, '/buttonList/mdf', NULL, NULL, 3, '1', '按钮管理页面修改按钮', '0', '2022-04-02 17:39:07', '2022-05-20 14:12:56', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m257777645981274112', 'm257665658706202624', '3', '删除', '2', NULL, '/buttonList/del', NULL, NULL, 4, '1', '按钮管理页面删除按钮', '0', '2022-04-02 17:40:04', '2022-05-20 14:12:53', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m257851541048791040', 'm257148503338258432', '3', '修改', '2', NULL, '/menuList/mdf', NULL, NULL, 3, '1', '菜单管理-修改', '0', '2022-04-02 22:33:43', '2022-05-20 14:10:07', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m257851973393453056', 'm257148503338258432', '3', '删除', '2', NULL, '/menuList/del', NULL, NULL, 4, '1', '菜单管理-删除', '0', '2022-04-02 22:35:26', '2022-05-20 14:10:04', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m257853564892745728', 'm257148503338258432', '3', '新增', '2', NULL, '/menuList/add', NULL, NULL, 2, '1', '菜单管理-新增', '0', '2022-04-02 22:41:46', '2022-05-20 14:10:11', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m258049993212956672', 'm257148294692605952', '2', '角色管理', '1', NULL, 'role', '/meetblog/auth/role', 'el-icon-s-data', 33, '1', '角色管理', '0', '2022-04-03 11:42:16', '2022-04-03 11:42:16', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m258125601385222144', 'm258049993212956672', '3', '新增', '2', NULL, '/role/add', NULL, NULL, 2, '1', '角色管理-新增', '0', '2022-04-03 16:42:43', '2022-05-20 14:13:28', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m258125817857445888', 'm258049993212956672', '3', '修改', '2', NULL, '/role/mdf', NULL, NULL, 3, '1', '角色管理-修改', '0', '2022-04-03 16:43:34', '2022-05-20 14:13:25', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m258126438455054336', 'm258049993212956672', '3', '删除', '2', NULL, '/role/del', NULL, NULL, 4, '1', '角色管理-删除', '0', '2022-04-03 16:46:02', '2022-05-20 14:13:22', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m258547944318308352', 'm257149844714426368', '2', '分类管理', '1', NULL, 'classfc', '/meetblog/blog/classfc', 'el-icon-table-lamp', 12, '1', '博客分类', '0', '2022-04-04 20:40:57', '2022-04-06 15:57:30', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m258918838005927936', 'm257150259094884352', '3', '新增', '2', NULL, '/role/add', NULL, NULL, 2, '1', '标签管理-新增', '0', '2022-04-05 21:14:45', '2022-05-20 14:13:57', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m258919727856881664', 'm257150259094884352', '3', '修改', '2', NULL, '/tag/mdf', NULL, NULL, 3, '1', '标签管理-修改', '0', '2022-04-05 21:18:17', '2022-05-20 14:13:50', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m258919807884201984', 'm257150259094884352', '3', '删除', '2', NULL, '/tag/del', NULL, NULL, 4, '1', '标签管理-删除', '0', '2022-04-05 21:18:36', '2022-05-20 14:13:43', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m259201686092910592', 'm258547944318308352', '3', '新增', '2', NULL, '/classfc/add', NULL, NULL, 2, '1', '分类管理-新增', '0', '2022-04-06 15:58:40', '2022-05-20 14:05:44', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m259201802937831424', 'm258547944318308352', '3', '修改', '2', NULL, '/classfc/mdf', NULL, NULL, 3, '1', '分类管理-修改', '0', '2022-04-06 15:59:08', '2022-05-20 14:05:39', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m259201888229003264', 'm258547944318308352', '3', '删除', '2', NULL, '/classfc/del', NULL, NULL, 4, '1', '分类管理-删除', '0', '2022-04-06 15:59:28', '2022-05-20 14:05:36', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m264278484136759296', 'm257371993995743232', '2', 'wangeditor', '1', NULL, 'https://www.wangeditor.com/v4/', NULL, 'el-icon-goods', 0, '1', 'wangeditor富文本编辑器', '1', '2022-04-20 16:12:04', '2022-04-20 16:12:48', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m274707599180435456', 'm257150080614666240', '3', '新增', '2', NULL, '/blog/add', NULL, NULL, 2, '1', '博客管理新增按钮', '0', '2022-05-19 10:53:39', '2022-05-20 11:23:09', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m274707820266393600', 'm257150080614666240', '3', '修改', '2', NULL, '/blog/mdf', NULL, NULL, 3, '1', '博客管理-修改', '0', '2022-05-19 10:54:31', '2022-05-20 11:23:00', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m274707955671109632', 'm257150080614666240', '3', '删除', '2', NULL, '/blog/del', NULL, NULL, 4, '1', '博客管理-删除', '0', '2022-05-19 10:55:04', '2022-05-20 11:22:57', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m275077333717618688', 'm257150080614666240', '3', '列表查询', '2', NULL, '/blog/list', NULL, NULL, 1, '1', '博客管理-列表查询', '0', '2022-05-20 11:22:50', '2022-05-20 11:23:14', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m275078241989955584', 'm257149207754838016', '3', '列表查询', '2', NULL, '/user/list', NULL, NULL, 1, '1', '用户列表-列表查询', '0', '2022-05-20 11:26:27', '2022-05-20 11:26:27', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m275078400450760704', 'm257149207754838016', '3', '新增', '2', NULL, '/user/add', NULL, NULL, 2, '1', '用户列表-新增', '0', '2022-05-20 11:27:05', '2022-05-20 11:27:05', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m275078615840854016', 'm257149207754838016', '3', '修改', '2', NULL, '/user/mdf', NULL, NULL, 3, '1', '用户列表-修改', '0', '2022-05-20 11:27:56', '2022-05-20 11:27:56', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m275078708866322432', 'm257149207754838016', '3', '详情', '2', NULL, '/user/info', NULL, NULL, 4, '1', '用户列表-详情', '0', '2022-05-20 11:28:18', '2022-05-20 11:28:18', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m275118260515508224', 'm258547944318308352', '3', '查询', '2', NULL, '/classfc/list', NULL, NULL, 1, '1', '分类管理-列表查询', '0', '2022-05-20 14:05:28', '2022-05-20 14:05:28', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m275118535812845568', 'm257150259094884352', '3', '查询', '2', NULL, '/role/list', NULL, NULL, 1, '1', '标签管理-列表查询', '0', '2022-05-20 14:06:34', '2022-05-20 14:06:34', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m275119284265422848', 'm257148503338258432', '3', '查询', '2', NULL, '/menuList/list', NULL, NULL, 1, '1', '菜单管理-查询', '0', '2022-05-20 14:09:32', '2022-05-20 14:09:58', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m275119700290048000', 'm258049993212956672', '3', '查询', '2', NULL, '/role/getlist', NULL, NULL, 1, '1', '角色管理-查询', '0', '2022-05-20 14:11:11', '2022-05-20 14:11:11', '0');
INSERT INTO `trans`.`b_menu`(`menu_id`, `high_lvl_id`, `menu_lvl`, `menu_nm`, `menu_type`, `permission_value`, `path`, `component`, `icon`, `sort`, `menu_stat`, `summy`, `link_flg`, `create_time`, `update_time`, `recd_stat`) VALUES ('m275120104373489664', 'm257665658706202624', '3', '查询', '2', NULL, '/buttonList/list', NULL, NULL, 1, '1', '按钮管理-查询', '0', '2022-05-20 14:12:48', '2022-05-20 14:12:48', '0');

-- b_role 权限表
INSERT INTO `trans`.`b_role`(`role_id`, `role_nm`, `role_code`, `role_stat`, `summy`, `create_time`, `update_time`, `recd_stat`) VALUES ('0', '普通用户', NULL, '1', '查询权限', '2022-03-24 14:44:00', '2022-04-03 16:26:56', '0');
INSERT INTO `trans`.`b_role`(`role_id`, `role_nm`, `role_code`, `role_stat`, `summy`, `create_time`, `update_time`, `recd_stat`) VALUES ('1', '管理员', NULL, '1', '拥有部分操作权限', '2022-03-24 14:44:00', '2022-04-03 16:26:57', '0');
INSERT INTO `trans`.`b_role`(`role_id`, `role_nm`, `role_code`, `role_stat`, `summy`, `create_time`, `update_time`, `recd_stat`) VALUES ('2', '超级管理员', NULL, '1', '拥有所有权限', '2022-03-24 14:44:00', '2022-04-03 16:26:57', '0');
