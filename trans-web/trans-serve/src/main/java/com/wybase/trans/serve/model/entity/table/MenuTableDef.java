package com.wybase.trans.serve.model.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class MenuTableDef extends TableDef {

    public static final MenuTableDef MENU = new MenuTableDef();

    public final QueryColumn ICON = new QueryColumn(this, "icon");
    public final QueryColumn PATH = new QueryColumn(this, "path");
    public final QueryColumn SORT = new QueryColumn(this, "sort");
    public final QueryColumn SUMMY = new QueryColumn(this, "summy");
    public final QueryColumn MENU_ID = new QueryColumn(this, "menu_id");
    public final QueryColumn MENU_NM = new QueryColumn(this, "menu_nm");
    public final QueryColumn LINK_FLG = new QueryColumn(this, "link_flg");
    public final QueryColumn MENU_LVL = new QueryColumn(this, "menu_lvl");
    public final QueryColumn MENU_STAT = new QueryColumn(this, "menu_stat");
    public final QueryColumn MENU_TYPE = new QueryColumn(this, "menu_type");
    public final QueryColumn RECD_STAT = new QueryColumn(this, "recd_stat");
    public final QueryColumn COMPONENT = new QueryColumn(this, "component");
    public final QueryColumn HIGH_LVL_ID = new QueryColumn(this, "high_lvl_id");
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");
    public final QueryColumn PERMISSION_VALUE = new QueryColumn(this, "permission_value");

    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ICON, PATH, SORT, SUMMY, MENU_ID, MENU_NM, LINK_FLG, MENU_LVL, MENU_STAT, MENU_TYPE, RECD_STAT, COMPONENT, HIGH_LVL_ID, CREATE_TIME, UPDATE_TIME, PERMISSION_VALUE};

    public MenuTableDef() {
        super("", "b_menu");
    }

}
