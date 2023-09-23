package com.wybase.trans.serve.model.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class RoleTableDef extends TableDef {

    public static final RoleTableDef ROLE = new RoleTableDef();

    public final QueryColumn SUMMY = new QueryColumn(this, "summy");
    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");
    public final QueryColumn ROLE_NM = new QueryColumn(this, "role_nm");
    public final QueryColumn RECD_STAT = new QueryColumn(this, "recd_stat");
    public final QueryColumn ROLE_CODE = new QueryColumn(this, "role_code");
    public final QueryColumn ROLE_STAT = new QueryColumn(this, "role_stat");
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{SUMMY, ROLE_ID, ROLE_NM, RECD_STAT, ROLE_CODE, ROLE_STAT, CREATE_TIME, UPDATE_TIME};

    public RoleTableDef() {
        super("", "b_role");
    }

}
