package com.wybase.trans.serve.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class EnumListTableDef extends TableDef {

    public static final EnumListTableDef ENUM_LIST = new EnumListTableDef();

    public final QueryColumn SEQ = new QueryColumn(this, "seq");
    public final QueryColumn KEY_ID = new QueryColumn(this, "key_id");
    public final QueryColumn KEY_NM = new QueryColumn(this, "key_nm");
    public final QueryColumn ENUM_ID = new QueryColumn(this, "enum_id");
    public final QueryColumn REMARK = new QueryColumn(this, "remark");
    public final QueryColumn RECD_STAT = new QueryColumn(this, "recd_stat");

    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{SEQ, KEY_ID, KEY_NM, ENUM_ID, REMARK, RECD_STAT};

    public EnumListTableDef() {
        super("", "b_enum_list");
    }

}
