<template>
  <div class="app-container">
    <!-- 上方搜索框   -->
    <div>
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-input v-model="searObj.routeId" clearable placeholder="路由ID" />
        </el-form-item>
        <el-button v-permission="'/role/getlist'" type="primary" icon="el-icon-search" @click="getRouteList()">查询
        </el-button>
        <el-button v-permission="'/role/add'" type="primary" icon="el-icon-document-add" @click="roleAdd">新增
        </el-button>
      </el-form>
    </div>
    <!-- 数据显示区域   -->
    <div>
      <el-table
        :data="routeList"
        style="width: 100%;margin-bottom: 20px;"
      >

        <el-table-column type="index" align="center" label="序号" width="50" />
        <el-table-column prop="routeId" align="center" label="路由ID" width="150" />
        <el-table-column prop="uri" align="center" label="路由地址" width="150" />
        <el-table-column prop="uriType" align="center" label="路由地址类型" width="150" />
        <el-table-column prop="content" align="center" label="路由简介" width="150" />
        <el-table-column prop="roleStat" align="center" label="状态" width="150">
          <template v-slot="scope">
            <el-switch
              v-model="scope.row.roleStat"
              disabled
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="1"
              inactive-value="0"
            />
          </template>
        </el-table-column>
        <el-table-column align="center" label="路由参数" width="150">
          <template v-slot="scope">
            <el-popover
              placement="top-start"
              width="30%"
              trigger="click"
            >
              <el-table :data="scope.row.routeParams">
                <el-table-column width="100" property="paramType" label="类型" />
                <el-table-column width="150" property="paramValue" label="参数值" />
                <el-table-column width="100" property="routeParamStat" label="状态" />
                <el-table-column width="150" property="content" label="描述" />
              </el-table>
              <el-button slot="reference">参数列表</el-button>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column fixed="right" align="center" label="操作" width="200">
          <template v-slot="scope">
            <el-tooltip v-permission="'/menuList/mdf'" class="item" effect="light" content="修改" placement="top">
              <el-button
                type="primary"
                icon="el-icon-edit"
                size="mini"
                @click="menuInfoQry(scope.row.menuId)"
              />
            </el-tooltip>
            <el-tooltip v-permission="'/menuList/del'" class="item" effect="light" content="删除" placement="top">
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                @click="menuDel(scope.row.menuId)"
              />
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
        :before-close="closeDialog"
      >
        <el-form>
          <el-form-item label="角色名称：" label-width="120px">
            <el-input v-model="roleInfo.roleNm" style="width: 80%" />
          </el-form-item>
          <el-form-item label="角色状态：" label-width="120px">
            <el-switch
              v-model="roleInfo.roleStat"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="1"
              inactive-value="0"
            />
          </el-form-item>
          <el-form-item label="角色简介：" label-width="120px">
            <el-input
              v-model="roleInfo.summy"
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 4}"
              maxlength="30"
              show-word-limit
              style="width: 80%"
            />
          </el-form-item>
          <el-form-item label="权限：" label-width="120px">
            <el-tree
              ref="tree"
              :data="menuTree"
              show-checkbox
              node-key="menuId"
              :props="props"
              :default-checked-keys="roleInfo.menuIdList"
            />
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="saveOrUpdate()">保存</el-button>
          <el-button type="primary" @click="closeDialog()">取消</el-button>
        </span>
      </el-dialog>
    </div>
    <div>
      <!--分页-->
      <el-pagination
        :current-page="current"
        :page-size="limit"
        :total="total"
        style="padding: 30px; text-align: center"
        layout="total,prev,pager,next,jumper"
        @current-change="getRouteList"
      />
    </div>
  </div>
</template>

<script>
import route from '@/api/meetblog/route'
import menu from '@/api/meetblog/menu'

export default {
  name: 'Role',
  data() {
    return {
      current: 1, // 当前页
      limit: 10, // 每页记录数
      total: 0, // 总页数
      routeList: [], // 角色列表
      searObj: {}, // 搜索框
      title: '', // 弹窗标题
      dialogVisible: false, // 弹窗开关标志
      operFlg: '', // 操作标志
      roleInfo: {
        menuIdList: []
      }, // 角色信息
      menuTree: [],
      props: {
        children: 'children',
        label: 'menuNm'
      }
    }
  },
  created() {
    this.getRouteList()
  },
  methods: {
    /* 查询角色列表*/
    getRouteList(page = 1) {
      this.searObj.pageNum = page
      this.searObj.pageSize = this.limit
      debugger
      route.getRouteList(this.searObj).then(res => {
        this.routeList = res.data.routePageInfo.records
        this.total = res.data.routePageInfo.totalRow
      })
    },

    /* 打开弹窗添加角色*/
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
    /* 新增或者修改*/
    saveOrUpdate() {
      // 得到选中树的UID
      const id = this.$refs.tree.getCheckedKeys()
      const prantId = this.$refs.tree.getHalfCheckedKeys()
      // 子节点和父节点合并
      this.roleInfo.menuIdList = id.concat(prantId)
      console.log('roleInfo', this.roleInfo)
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
    /* 根据ID查询角色信息*/
    roleInfoQry(roleId) {
      this.operFlg = '1'
      var obj = {}
      obj.roleId = roleId
      role.roleInfoQry(obj).then(res => {
        this.roleInfo = res.data.roleInfo
        this.getMenuButtonTree()
        setTimeout(() => {
          res.data.roleMenuRelatnList.forEach((item) => {
            this.$refs.tree.setChecked(item, true, false)
          })
        }, 200)
        this.title = '角色修改'
        this.dialogVisible = true
      })
    },
    /* 查询菜单列表*/
    getMenuButtonTree() {
      var sear = {}
      menu.getMenuButtonTree(sear).then(res => {
        this.menuTree = res.data.menuButtonTree
      })
    },
    roleDel(roleId) {
      this.$confirm('此操作将删除菜单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var obj = {}
        obj.roleId = roleId
        role.roleInfoDel(obj).then(res => {
          // 提示
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
        })
      })
    }
  }
}
</script>

<style scoped>

</style>
