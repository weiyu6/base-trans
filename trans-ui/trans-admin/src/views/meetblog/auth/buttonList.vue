<template>
  <div class="app-container">
    <!--    页头操作框-->
    <div>
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-cascader
            v-model="menuHighLvlId"
            filterable
            clearable
            placeholder="试试搜索：按钮管理"
            :options="menuTreeList"
            :props="props"
          />
        </el-form-item>

        <el-button v-permission="'/buttonList/list'" type="primary" icon="el-icon-search" @click="buttonTreeQry">查询</el-button>
        <!--        -->
        <el-button v-permission="'/buttonList/add'" type="primary" icon="el-icon-document-add" @click="addButton">添加
        </el-button>
      </el-form>
    </div>
    <!-- 数据显示区域 -->
    <div>
      <el-table
        :data="buttonTree"
        style="width: 100%;margin-bottom: 20px;"
        row-key="menuId"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      >
        <el-table-column prop="menuNm" label="菜单名称" width="180" />
        <el-table-column prop="menuType" label="菜单类型">
          <template v-slot="scope">
            <el-tag
              v-for="item in menuTypeEnum"
              v-if="scope.row.menuType === item.keyId"
              :type="item.keyNm=='菜单' ?'warning':'success'"
            >
              {{ item.keyNm }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="path" label="路由" width="150" />

        <el-table-column prop="summy" label="菜单简介" width="200" />
        <el-table-column prop="sort" label="排序" />
        <el-table-column prop="menuStat" label="菜单状态">
          <template v-slot="scope">
            <el-switch
              v-model="scope.row.menuStat"
              disabled
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="1"
              inactive-value="0"
            />
          </template>
        </el-table-column>

        <el-table-column fixed="right" align="center" label="操作" width="200">
          <template v-slot="scope">
            <el-tooltip v-if="scope.row.menuLvl === '3' " class="item" effect="light" content="修改" placement="top">
              <el-button
                v-permission="'/buttonList/mdf'"
                type="primary"
                icon="el-icon-edit"
                size="mini"
                @click="menuInfoQry(scope.row.menuId)"
              />
            </el-tooltip>
            <el-tooltip v-if="scope.row.menuLvl === '3' " class="item" effect="light" content="删除" placement="top">
              <el-button
                v-permission="'/buttonList/del'"
                type="danger"
                icon="el-icon-delete"
                size="mini"
                @click="buttonDel(scope.row.menuId)"
              />
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
        :before-close="closeDialog"
      >

        <el-form class="demo-form-inline">
          <el-form-item label="菜单名称：" label-width="120px">
            <el-input v-model="buttonForm.menuNm" style="width: 80%" />
          </el-form-item>
          <el-form-item label="路由：" label-width="120px">
            <el-input v-model="buttonForm.path" style="width: 80%" />
          </el-form-item>

          <el-form-item label="父菜单名：" label-width="120px">
            <el-select
              v-model="buttonForm.highLvlId"
              style="width: 80%"
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
            <el-input-number
              v-model="buttonForm.sort"
              controls-position="right"
              :min="0"
              style="width: 60%"
            />
          </el-form-item>
          <el-form :inline="true">

            <el-form-item label="菜单状态：" label-width="120px">
              <el-switch
                v-model="buttonForm.menuStat"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-value="1"
                inactive-value="0"
              />
            </el-form-item>
            <el-form-item label="外链标志：" label-width="120px">
              <el-switch
                v-model="buttonForm.linkFlg"
                disabled
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-value="1"
                inactive-value="0"
              />
            </el-form-item>
          </el-form>

          <el-form-item label="菜单简介：" label-width="120px">
            <el-input
              v-model="buttonForm.summy"
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 4}"
              maxlength="30"
              show-word-limit
              style="width: 80%"
            />
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
import menu from '@/api/meetblog/menu'
import enumlist from '@/api/meetblog/enumlist'

export default {
  name: 'ButtonList',
  data() {
    return {
      buttonTree: [], // 按钮集合
      enumIdList: {}, // 枚举列表
      menuTypeEnum: [], // 菜单类型枚举
      buttonForm: {}, // 按钮表单
      menuLvlList: [], // 菜单级别列表
      menuTreeList: [], // 搜索框菜单列表
      dialogVisible: false, // 弹窗开关标志
      title: '', // 标题
      operFlg: '',
      menuOptions: [], // 二级菜单候选项

      menuHighLvlId: '',
      props: {
        value: 'menuId', // 指定选项标签为选项对象的某个属性值
        label: 'menuNm', // 指定选项标签为选项对象的某个属性值
        children: 'children' // 指定选项的子选项为选项对象的某个属性值
      }

    }
  },
  created() {
    this.getButtonTree()
    this.getMenuTree()
    this.getEnumList()
  },
  methods: {
    /* 查询按钮列表*/
    getButtonTree() {
      var sear = {}
      menu.getButtonTree(sear).then(res => {
        this.buttonTree = res.data.buttonTree
      })
    },
    /* 查询出枚举列表*/
    getEnumList() {
      this.enumIdList.enumIds = [{ enumId: 'MENU_TYPE' }]
      enumlist.getEnumList(this.enumIdList).then(res => {
        var enumMap = res.data
        this.menuTypeEnum = enumMap.MENU_TYPE
      })
    },
    /* 查询菜单列表*/
    getMenuTree() {
      var sear = {}
      menu.getMenuTree(sear).then(res => {
        this.menuTreeList = this.getTreeData(res.data.menuTree)
      })
    },

    /* 根据条件查询按钮树形列表*/
    buttonTreeQry() {
      var sear = {}

      var menuId = this.menuHighLvlId
      if (menuId.length > 0) {
        // 选取最后一个元素
        sear.menuId = menuId[menuId.length - 1]
      }
      menu.getButtonTree(sear).then(res => {
        this.buttonTree = res.data.buttonTree
      })
    },
    /* 打开弹窗添加用户*/
    addButton() {
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
      this.buttonForm = {}
    },
    // 菜单远程搜索函数
    remoteMethod(query) {
      if (query !== '') {
        var params = {}
        params.menuLvl = 2
        params.menuNm = query
        menu.getMenuList(params).then(res => {
          this.menuOptions = res.data.menuListQry
        })
      } else {
        this.menuOptions = []
      }
    },
    /* 保存或修改操作*/
    saveOrUpdate() {
      this.buttonForm.menuType = '2'
      this.buttonForm.linkFlg = '0'
      if (this.operFlg == '1') {
        menu.buttonMdf(this.buttonForm).then(res => {
          // 提示
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          this.closeDialog()
          this.getButtonTree()
        })
      } else if (this.operFlg == '2') {
        menu.buttonAdd(this.buttonForm).then(res => {
          // 提示
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          this.closeDialog()
          this.getButtonTree()
        })
      }
    },
    /* 根据菜单ID查询菜单信息*/
    menuInfoQry(menuId) {
      var sear = {}
      sear.menuId = menuId
      menu.menuInfoQry(sear).then(res => {
        this.buttonForm = res.data.menu
        this.remoteMethod(this.buttonForm.highLvlId)
        this.operFlg = '1'
        this.title = '修改按钮'
        this.dialogVisible = true
      })
    },
    /* 删除按钮*/
    buttonDel(menuId) {
      this.$confirm('此操作将删除按钮, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var sear = {}
        sear.menuId = menuId
        menu.menuDelById(sear).then(res => {
          // 提示
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.getButtonTree()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // 递归判断列表，把最后的children设为undefined
    getTreeData(data) {
      for (var i = 0; i < data.length; i++) {
        if (data[i].children.length < 1) {
          // children若为空数组，则将children设为undefined
          data[i].children = undefined
        } else {
          // children若不为空数组，则继续 递归调用 本方法
          this.getTreeData(data[i].children)
        }
      }
      return data
    }

  }
}
</script>

<style scoped>

</style>
