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
        <el-button v-permission="'/role/add'" type="primary" icon="el-icon-document-add" @click="routeAdd">新增
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
        <el-table-column prop="routeStat" align="center" label="状态" width="150">
          <template v-slot="scope">
            <el-switch
              v-model="scope.row.routeStat"
              disabled
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="0"
              inactive-value="1"
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
                @click="routeQry(scope.row.routeId)"
              />
            </el-tooltip>
            <el-tooltip v-permission="'/menuList/del'" class="item" effect="light" content="删除" placement="top">
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                @click="routeDel(scope.row.routeId)"
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
        width="50%"
        :visible.sync="dialogVisible"
        :before-close="closeDialog"
      >
        <el-form>
          <el-form-item label="路由ID：" label-width="120px">
            <el-input v-model="routeInfo.routeId" style="width: 80%" />
          </el-form-item>
          <el-form-item label="路由地址类型：" label-width="120px">
            <el-input v-model="routeInfo.uriType" style="width: 80%" />
          </el-form-item>
          <el-form-item label="路由地址：" label-width="120px">
            <el-input v-model="routeInfo.uri" style="width: 80%" />
          </el-form-item>
          <el-form-item label="路由状态：" label-width="120px">
            <el-switch
              v-model="routeInfo.routeStat"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="0"
              inactive-value="1"
            />
          </el-form-item>
          <el-form-item label="路由简介：" label-width="120px">
            <el-input
              v-model="routeInfo.content"
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 4}"
              maxlength="30"
              show-word-limit
              style="width: 80%"
            />
          </el-form-item>
          <el-form-item
            v-for="(param, index) in routeInfo.routeParams"
            :key="param.id"
            label-width="120px"
            :label="'参数' + index + '：'"
            :prop="'param.' + index + 'id'"
          >
            <el-input v-model="param.paramValue" style="width: 50%" />
            <el-tooltip class="item" effect="light" content="添加" placement="top">
              <el-button
                type="primary"
                icon="el-icon-edit"
                size="mini"
                @click="addDomain(param)"
              />
            </el-tooltip>
            <el-tooltip class="item" effect="light" content="删除" placement="top">
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                @click="removeDomain(param)"
              />
            </el-tooltip>
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
      routeInfo: {
        routeParams: [
          {
            paramType: '1',
            paramValue: '',
            routeParamStat: '1'
          }
        ]
      }, // 路由
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
    /* 查询路由列表*/
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
    routeAdd() {
      this.operFlg = '2'
      this.title = '新增路由'
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
      console.log('roleInfo', this.routeInfo)
    },
    removeDomain(item) {
      const index = this.routeInfo.routeParams.indexOf(item)
      if (index !== 0) {
        this.routeInfo.routeParams.splice(index, 1)
      }
    },
    addDomain() {
      this.routeInfo.routeParams.push({
        value: '',
        key: Date.now()
      })
    },
    /* 根据ID查询角色信息*/
    routeQry(roleId) {
      this.operFlg = '1'
      var obj = {}
      obj.roleId = roleId
      route.roleInfoQry(obj).then(res => {
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
    routeDel(roleId) {
      this.$confirm('此操作将删除菜单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const obj = {}
        obj.roleId = roleId
        route.roleInfoDel(obj).then(() => {
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
