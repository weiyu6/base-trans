<template>
  <div class="app-container">
    <!-- 上方搜索框   -->
    <div>
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-input v-model="searobj.roleNm" clearable placeholder="角色名"></el-input>
        </el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="getRoleList" v-permission="'/role/getlist'">查询</el-button>
        <el-button type="primary" icon="el-icon-document-add" @click="roleAdd" v-permission="'/role/add'">新增
        </el-button>
      </el-form>
    </div>
    <!-- 数据显示区域   -->
    <div>
      <el-table
        :data="roleList"
        style="width: 70%">

        <el-table-column type="index" align="center" label="序号" width="50"/>
        <el-table-column prop="roleNm" align="center" label="角色名称" width="150"/>
        <el-table-column prop="summy" align="center" label="角色描述" width="150"/>
        <el-table-column prop="roleStat" align="center" label="状态" width="150">
          <template slot-scope="scope">
            <el-switch
              disabled
              v-model="scope.row.roleStat"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="1"
              inactive-value="0"/>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" align="center" label="创建时间" width="230">
          <template slot-scope="scope">
            <el-date-picker
              disabled
              v-model="scope.row.createTime"
              type="datetime"
            />
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="light" content="修改" placement="top" v-if="scope.row.roleId != 2" v-permission="'/role/mdf'">
              <el-button type="primary" icon="el-icon-edit" size="mini"
                         @click="roleInfoQry(scope.row.roleId)"/>
            </el-tooltip>
            <el-tooltip class="item" effect="light" content="删除" placement="top" v-if="scope.row.roleId != 2" v-permission="'/role/del'">
              <el-button type="danger" icon="el-icon-delete" size="mini"
                         @click="roleDel(scope.row.roleId)"/>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 弹窗    -->
    <div>
      <el-dialog
        :title="title"
        center
        width="40%"
        :visible.sync="dialogVisible"
        :before-close="closeDialog">
        <el-form>
          <el-form-item label="角色名称：" label-width="120px">
            <el-input v-model="roleInfo.roleNm" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="角色状态：" label-width="120px">
            <el-switch
              v-model="roleInfo.roleStat"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="1"
              inactive-value="0"/>
          </el-form-item>
          <el-form-item label="角色简介：" label-width="120px">
            <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" v-model="roleInfo.summy" maxlength="30"
                      show-word-limit style="width: 80%"/>
          </el-form-item>
          <el-form-item label="权限：" label-width="120px">
            <el-tree
              ref="tree"
              :data="menuTree"
              show-checkbox
              node-key="menuId"
              :props="props"
              :default-checked-keys="roleInfo.menuIdList"
            ></el-tree>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="saveOrUpdate()">保存</el-button>
          <el-button type="primary" @click="closeDialog()">取消</el-button>
        </span>
      </el-dialog>
    </div>


  </div>
</template>

<script>
import role from "@/api/meetblog/role";
import menu from "@/api/meetblog/menu";

export default {
  name: "role",
  data() {
    return {
      roleList: [],// 角色列表
      searobj: {},// 搜索框
      title: '',// 弹窗标题
      dialogVisible: false,//弹窗开关标志
      operFlg: '',// 操作标志
      roleInfo: {
        menuIdList: [],
      },// 角色信息
      menuTree: [],
      props: {
        children: "children",
        label: "menuNm"
      },
    }
  },
  created() {
    this.getRoleList()
  },
  methods: {
    /*查询角色列表*/
    getRoleList() {
      var sear = {}
      sear.roleNm = this.searobj.roleNm
      role.getRoleList(sear).then(res => {
        this.roleList = res.data.roleList
      })
    },

    /*打开弹窗添加角色*/
    roleAdd() {
      this.operFlg = '2'
      this.title = '新增角色'
      this.getMenuButtonTree()
      this.dialogVisible = true
    },
    /**
     * 关闭弹窗
     */
    closeDialog() {
      this.operFlg = ''
      this.dialogVisible = false
      this.roleInfo = {}
    },
    /*新增或者修改*/
    saveOrUpdate() {
      debugger;
      //得到选中树的UID
      const id = this.$refs.tree.getCheckedKeys()
      const prantId = this.$refs.tree.getHalfCheckedKeys()
      // 子节点和父节点合并
      this.roleInfo.menuIdList = id.concat(prantId)
      console.log("roleInfo", this.roleInfo)
      if (this.operFlg == '1') {
        role.roleInfoMdf(this.roleInfo).then(res => {
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          this.closeDialog()
          this.getRoleList()
        })
      } else if (this.operFlg == '2') {
        role.roleInfoAdd(this.roleInfo).then(res => {
          this.$message({
            type: 'success',
            message: '新增成功!'
          })
          this.closeDialog()
          this.getRoleList()
        })
      }

    },
    /*根据ID查询角色信息*/
    roleInfoQry(roleId) {
      this.operFlg = '1'
      var obj = {}
      obj.roleId = roleId
      role.roleInfoQry(obj).then(res => {
        this.roleInfo = res.data.roleInfo
        this.getMenuButtonTree()
        setTimeout(() => {
          res.data.roleMenuRelatnList.forEach((item) => {
            this.$refs.tree.setChecked(item, true, false);
          });
        }, 200);
        this.title = '角色修改'
        this.dialogVisible = true
      })
    },
    /*查询菜单列表*/
    getMenuButtonTree() {
      var sear = {}
      menu.getMenuButtonTree(sear).then(res => {
        this.menuTree = res.data.menuButtonTree
      })
    },
    roleDel(roleId) {
      this.$confirm("此操作将删除菜单, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        var obj = {}
        obj.roleId = roleId
        role.roleInfoDel(obj).then(res => {
          //提示
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.getRoleList()
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
