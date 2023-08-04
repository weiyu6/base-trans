<template>
  <div class="app-container">
    <!--    页头操作框-->
    <div>
      <el-form :inline="true" class="demo-form-inline">
        <el-button type="primary" icon="el-icon-search" @click="getMenuTree" v-permission=" '/menuList/list' ">刷新</el-button>
        <el-button type="primary" icon="el-icon-document-add" @click="addMenu" v-permission=" '/menuList/add' ">添加
        </el-button>
      </el-form>
    </div>
    <!-- 数据显示区域 -->
    <div>
      <el-table
        :data="menuTree"
        style="width: 100%;margin-bottom: 20px;"
        row-key="menuId"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
        <el-table-column prop="menuNm" label="  菜单名称" width="180"></el-table-column>

        <el-table-column prop="path"  label="路由" width="150"></el-table-column>
        <el-table-column prop="component" label="组件路径" width="180"></el-table-column>
        <el-table-column prop="summy" label="菜单简介" width="250"></el-table-column>
        <el-table-column prop="icon" label="图标">
          <template slot-scope="scope">
            <i :class="scope.row.icon"/>
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序" ></el-table-column>
        <el-table-column prop="menuStat" label="菜单状态">
          <template slot-scope="scope">
            <el-switch
              disabled
              v-model="scope.row.menuStat"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="1"
              inactive-value="0"/>
          </template>
        </el-table-column>

        <el-table-column prop="linkFlg" label="外链标志">
          <template slot-scope="scope">
            <el-switch
              disabled
              v-model="scope.row.linkFlg"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="1"
              inactive-value="0"/>
          </template>
        </el-table-column>

        <el-table-column fixed="right" align="center" label="操作" width="200">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="light" content="修改" placement="top" v-permission="'/menuList/mdf'">
              <el-button type="primary" icon="el-icon-edit" size="mini"
                         @click="menuInfoQry(scope.row.menuId)"/>
            </el-tooltip>
            <el-tooltip class="item" effect="light" content="删除" placement="top" v-permission="'/menuList/del'">
              <el-button type="danger" icon="el-icon-delete" size="mini"
                         @click="menuDel(scope.row.menuId)"/>
            </el-tooltip>
          </template>

        </el-table-column>
      </el-table>
    </div>

<!-- 弹窗   -->
    <div>
      <el-dialog
        center
        :title="title"
        :visible.sync="dialogVisible"
        width="40%"
        :before-close="closeDialog">

        <el-form class="demo-form-inline">

          <el-form-item label="菜单名称：" label-width="120px">
            <el-input v-model="menuForm.menuNm" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="路由：" label-width="120px">
            <el-input v-model="menuForm.path" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="菜单路径：" label-width="120px">
            <el-input v-model="menuForm.component" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="菜单图标：" label-width="120px">
            <el-input v-model="menuForm.icon" placeholder="请输入前图标名称" style="width: 80%">
              <el-button slot="append" icon="el-icon-setting" @click="openIconsDialog('prefix-icon')">
                选择
              </el-button>
            </el-input>
          </el-form-item>
          <el-form-item label="父菜单名：" label-width="120px">
            <el-select
              style="width: 80%"
              v-model="menuForm.highLvlId"
              filterable
              clearable
              remote
              reserve-keyword
              placeholder="请输入父菜单名"
              :remote-method="remoteMethod"
              :loading="false"
            >
              <el-option
                v-for="item in menuOptions"
                :key="item.menuId"
                :label="item.menuNm"
                :value="item.menuId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="排序：" label-width="120px">
            <!--            :max="10"-->
            <el-input-number v-model="menuForm.sort" controls-position="right" :min="0"
                             style="width: 60%"></el-input-number>
          </el-form-item>
          <el-form :inline="true">

            <el-form-item label="菜单状态：" label-width="120px">
              <el-switch
                v-model="menuForm.menuStat"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-value="1"
                inactive-value="0"/>
            </el-form-item>
            <el-form-item label="外链标志：" label-width="120px">
              <el-switch
                v-model="menuForm.linkFlg"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-value="1"
                inactive-value="0"/>
            </el-form-item>
          </el-form>


          <!--          <el-form-item label="菜单级别：" label-width="120px">
                      &lt;!&ndash; <el-input v-model="userinfoSig.userTag"/>&ndash;&gt;
                      <el-select clearable placeholder="菜单级别" style="width: 80%"
                                 v-model="menuForm.menuLvl">
                        <el-option
                          :key="item.enumId+''+item.seq"
                          :label="item.keyNm"
                          :value="item.keyId"
                          v-for="item in menuLvlList"
                        />
                      </el-select>
                    </el-form-item>-->
          <el-form-item label="菜单简介：" label-width="120px">
            <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" v-model="menuForm.summy" maxlength="30"
                      show-word-limit style="width: 80%"/>
          </el-form-item>

        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="saveOrUpdate()">保存</el-button>
          <el-button type="primary" @click="closeDialog()">取消</el-button>
        </span>


      </el-dialog>
      <icons-dialog :visible.sync="iconsVisible" :current="menuForm.icon" @select="setIcon"/>
    </div>

  </div>
</template>

<script>
import menu from "@/api/meetblog/menu";
import enumlist from "@/api/meetblog/enumlist";
import IconsDialog from "@/components/IconsDialog";

export default {
  name: "menuList",
  components: {
    IconsDialog
  },
  data() {
    return {
      menuTree: [], // 菜单集合
      enumIdList: {},// 枚举列表
      dialogVisible: false,// 弹窗开关标志
      title: '', //标题
      menuForm: {},// 菜单表单
      menuLvlList: [],// 菜单级别列表

      iconsVisible: false, // 是否显示icon选择器
      activeData: '', // 激活的图标
      menuOptions: [], //一级菜单候选项
      operFlg: '',// 操作标志：1-修改，2-添加
    }
  },
  created() {
    this.getMenuTree()
    this.getEnumList()
  },
  methods: {
    /*查询菜单列表*/
    getMenuTree() {
      var sear = {}
      menu.getMenuTree(sear).then(res => {

        this.menuTree = res.data.menuTree

      })
    },
    /*查询出枚举列表*/
    getEnumList() {
      this.enumIdList.enumIds = [{enumId: 'MENU_LVL'}]
      enumlist.getEnumList(this.enumIdList).then(res => {
        var enumMap = res.data;
        this.menuLvlList = enumMap.MENU_LVL
      })
    },
    /*打开弹窗添加用户*/
    addMenu() {
      this.operFlg = '2'
      this.title = '添加菜单'
      this.dialogVisible = true
    },
    /**
     * 关闭弹窗
     */
    closeDialog() {
      this.operFlg = ''
      this.dialogVisible = false
      this.menuOptions = []
      this.menuForm = {}
    },
    openIconsDialog(model) {
      this.iconsVisible = true
      this.currentIconModel = model
    },
    // 选择图标
    setIcon(val) {
      this.menuForm.icon = val
    },
    //菜单远程搜索函数
    remoteMethod(query) {
      if (query !== "") {
        var params = {};
        params.menuLvl = 1
        params.menuNm = query
        menu.getMenuList(params).then(res => {

          this.menuOptions = res.data.menuListQry;
        });
      } else {
        this.menuOptions = [];
      }
    },
    /*保存或修改操作*/
    saveOrUpdate() {

      this.menuForm.menuType = '1'
      if (this.operFlg == '1') {
        menu.menuMdf(this.menuForm).then(res => {
          //提示
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          this.closeDialog()
          this.getMenuTree()
        })
      } else if (this.operFlg == '2') {
        menu.menuAdd(this.menuForm).then(res => {
          //提示
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          this.closeDialog()
          this.getMenuTree()
        })
      }

    },
    /*根据菜单ID查询菜单信息*/
    menuInfoQry(menuId) {
      var sear = {}
      sear.menuId = menuId
      menu.menuInfoQry(sear).then(res => {
        this.menuForm = res.data.menu
        this.remoteMethod(this.menuForm.highLvlId)
        this.operFlg = '1'
        this.title = '修改菜单'
        this.dialogVisible = true
      })
    },
    menuDel(menuId) {
      this.$confirm("此操作将删除菜单, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        var sear = {}
        sear.menuId = menuId
        menu.menuDelById(sear).then(res => {
          //提示
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.getMenuTree()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    }

  }
}
</script>

<style scoped>

</style>
