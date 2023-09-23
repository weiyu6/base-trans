package com.wybase.trans.serve.model.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class UserInfoTableDef extends TableDef {

    public static final UserInfoTableDef USER_INFO = new UserInfoTableDef();

    public final QueryColumn OS = new QueryColumn(this, "os");
    public final QueryColumn UUID = new QueryColumn(this, "uuid");
    public final QueryColumn EMAIL = new QueryColumn(this, "email");
    public final QueryColumn IP_SRC = new QueryColumn(this, "ip_src");
    public final QueryColumn QQ_NUM = new QueryColumn(this, "qq_num");
    public final QueryColumn SUMMY = new QueryColumn(this, "summy");
    public final QueryColumn AVATAR = new QueryColumn(this, "avatar");
    public final QueryColumn GENDER = new QueryColumn(this, "gender");
    public final QueryColumn MOBILE = new QueryColumn(this, "mobile");
    public final QueryColumn NICK_NM = new QueryColumn(this, "nick_nm");
    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");
    public final QueryColumn SOURCE = new QueryColumn(this, "source");
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");
    public final QueryColumn USER_NM = new QueryColumn(this, "user_nm");
    public final QueryColumn BROWSER = new QueryColumn(this, "browser");
    public final QueryColumn USER_TAG = new QueryColumn(this, "user_tag");
    public final QueryColumn BIRTHDAY = new QueryColumn(this, "birthday");
    public final QueryColumn PASS_WORD = new QueryColumn(this, "pass_word");
    public final QueryColumn RECD_STAT = new QueryColumn(this, "recd_stat");
    public final QueryColumn VALID_CODE = new QueryColumn(this, "valid_code");
    public final QueryColumn WECHAT_NUM = new QueryColumn(this, "wechat_num");
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
    public final QueryColumn LOGIN_COUNT = new QueryColumn(this, "login_count");
    public final QueryColumn PROFESSION = new QueryColumn(this, "profession");
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");
    public final QueryColumn COMMENT_STAT = new QueryColumn(this, "comment_stat");
    public final QueryColumn LAST_LOGIN_IP = new QueryColumn(this, "last_login_ip");
    public final QueryColumn LOADING_VALID = new QueryColumn(this, "loading_valid");
    public final QueryColumn TRANS_RECD_NUM = new QueryColumn(this, "trans_recd_num");
    public final QueryColumn LAST_LOGIN_TIME = new QueryColumn(this, "last_login_time");
    public final QueryColumn START_EMAIL_NOTICE = new QueryColumn(this, "start_email_notice");

    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{OS, UUID, EMAIL, IP_SRC, QQ_NUM, SUMMY, AVATAR, GENDER, MOBILE, NICK_NM, ROLE_ID, SOURCE, USER_ID, USER_NM, BROWSER, USER_TAG, BIRTHDAY, PASS_WORD, RECD_STAT, VALID_CODE, WECHAT_NUM, CREATE_TIME, LOGIN_COUNT, PROFESSION, UPDATE_TIME, COMMENT_STAT, LAST_LOGIN_IP, LOADING_VALID, TRANS_RECD_NUM, LAST_LOGIN_TIME, START_EMAIL_NOTICE};

    public UserInfoTableDef() {
        super("", "b_user_info");
    }

}
