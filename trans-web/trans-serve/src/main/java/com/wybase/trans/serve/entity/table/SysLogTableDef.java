package com.wybase.trans.serve.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class SysLogTableDef extends TableDef {

    public static final SysLogTableDef SYS_LOG = new SysLogTableDef();

    public final QueryColumn IP = new QueryColumn(this, "ip");
    public final QueryColumn OS = new QueryColumn(this, "os");
    public final QueryColumn URL = new QueryColumn(this, "url");
    public final QueryColumn CHNL = new QueryColumn(this, "chnl");
    public final QueryColumn IP_SRC = new QueryColumn(this, "ip_src");
    public final QueryColumn LOG_ID = new QueryColumn(this, "log_id");
    public final QueryColumn METHOD = new QueryColumn(this, "method");
    public final QueryColumn PARAMS = new QueryColumn(this, "params");
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");
    public final QueryColumn USER_NM = new QueryColumn(this, "user_nm");
    public final QueryColumn BROWSER = new QueryColumn(this, "browser");
    public final QueryColumn REQ_TYPE = new QueryColumn(this, "req_type");
    public final QueryColumn METHOD_NM = new QueryColumn(this, "method_nm");
    public final QueryColumn MODULE_ID = new QueryColumn(this, "module_id");
    public final QueryColumn RECD_STAT = new QueryColumn(this, "recd_stat");
    public final QueryColumn CLASS_PATH = new QueryColumn(this, "class_path");
    public final QueryColumn OTHER_DATA = new QueryColumn(this, "other_data");
    public final QueryColumn TRANS_TYPE = new QueryColumn(this, "trans_type");
    public final QueryColumn CONSUM_TIME = new QueryColumn(this, "consum_time");
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");
    public final QueryColumn TRANS_RECD_NUM = new QueryColumn(this, "trans_recd_num");

    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{IP, OS, URL, CHNL, IP_SRC, LOG_ID, METHOD, PARAMS, USER_ID, USER_NM, BROWSER, REQ_TYPE, METHOD_NM, MODULE_ID, RECD_STAT, CLASS_PATH, OTHER_DATA, TRANS_TYPE, CONSUM_TIME, CREATE_TIME, UPDATE_TIME, TRANS_RECD_NUM};

    public SysLogTableDef() {
        super("", "b_sys_log");
    }

}
