package com.wybase.trans.serve.model.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class TransRecordTableDef extends TableDef {

    public static final TransRecordTableDef TRANS_RECORD = new TransRecordTableDef();

    public final QueryColumn OS = new QueryColumn(this, "os");
    public final QueryColumn URL = new QueryColumn(this, "url");
    public final QueryColumn CHNL = new QueryColumn(this, "chnl");
    public final QueryColumn IP_SRC = new QueryColumn(this, "ip_src");
    public final QueryColumn IP_ADDR = new QueryColumn(this, "ip_addr");
    public final QueryColumn METHOD = new QueryColumn(this, "method");
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");
    public final QueryColumn BROWSER = new QueryColumn(this, "browser");
    public final QueryColumn CHNL_SRC = new QueryColumn(this, "chnl_src");
    public final QueryColumn MAC_ADDR = new QueryColumn(this, "mac_addr");
    public final QueryColumn REQ_DATE = new QueryColumn(this, "req_date");
    public final QueryColumn REQ_TYPE = new QueryColumn(this, "req_type");
    public final QueryColumn RECD_STAT = new QueryColumn(this, "recd_stat");
    public final QueryColumn USER_NAME = new QueryColumn(this, "user_name");
    public final QueryColumn REQ_RECD_ID = new QueryColumn(this, "req_recd_id");
    public final QueryColumn TRANS_DATE = new QueryColumn(this, "trans_date");
    public final QueryColumn CONSUM_TIME = new QueryColumn(this, "consum_time");
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
    public final QueryColumn GLOB_RECD_ID = new QueryColumn(this, "glob_recd_id");
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");
    public final QueryColumn TRANS_RECD_ID = new QueryColumn(this, "trans_recd_id");
    public final QueryColumn TRANS_STATUS = new QueryColumn(this, "trans_status");

    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{OS, URL, CHNL, IP_SRC, IP_ADDR, METHOD, USER_ID, BROWSER, CHNL_SRC, MAC_ADDR, REQ_DATE, REQ_TYPE, RECD_STAT, USER_NAME, REQ_RECD_ID, TRANS_DATE, CONSUM_TIME, CREATE_TIME, GLOB_RECD_ID, UPDATE_TIME, TRANS_RECD_ID, TRANS_STATUS};

    public TransRecordTableDef() {
        super("", "b_trans_record");
    }

}
