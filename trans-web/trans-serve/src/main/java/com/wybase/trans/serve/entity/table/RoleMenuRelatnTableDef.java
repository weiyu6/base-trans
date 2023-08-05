package com.wybase.trans.serve.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class RoleMenuRelatnTableDef extends TableDef {

    public static final RoleMenuRelatnTableDef ROLE_MENU_RELATN = new RoleMenuRelatnTableDef();

    public final QueryColumn RMR_ID = new QueryColumn(this, "rmr_id");
    public final QueryColumn MENU_ID = new QueryColumn(this, "menu_id");
    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");
    public final QueryColumn RECD_STAT = new QueryColumn(this, "recd_stat");
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{RMR_ID, MENU_ID, ROLE_ID, RECD_STAT, CREATE_TIME, UPDATE_TIME};

    public RoleMenuRelatnTableDef() {
        super("", "b_role_menu_relatn");
    }

}
