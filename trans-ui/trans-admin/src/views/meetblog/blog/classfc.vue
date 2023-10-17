<template>
  <div class="app-container">
    <!--搜索框-->
    <div>
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-input v-model="searchObj.classfcNm" clearable placeholder="分类名" />
        </el-form-item>
        <el-button v-permission="'/classfc/list'" type="primary" icon="el-icon-search" @click="getClassfcList()">查询</el-button>
        <el-button v-permission="'/classfc/add'" type="primary" icon="el-icon-document-add" @click="classfcAdd">新增
        </el-button>
      </el-form>
    </div>
    <!--数据显示-->
    <div>
      <el-table :data="classfcList" style="width: 100%">
        <el-table-column fixed type="index" align="center" width="50" label="序号" />
        <el-table-column prop="classfcNm" align="center" label="标签名" width="150" />
        <el-table-column prop="content" align="center" label="标签简介" width="150" />
        <el-table-column prop="clickCount" sortable align="center" label="点击数" width="120" />
        <el-table-column prop="sort" sortable align="center" label="排序" width="120" />
        <el-table-column prop="createTime" align="center" label="创建时间" width="240">
          <template v-slot="scope">
            <el-date-picker
              v-model="scope.row.createTime"
              disabled
              type="datetime"
            />
          </template>
        </el-table-column>
        <el-table-column align="left" label="操作">
          <template v-slot="slot">
            <el-tooltip v-permission="'/classfc/mdf'" class="item" effect="light" content="修改" placement="top">
              <el-button type="primary" icon="el-icon-edit" size="mini" @click="classfcMdf(slot.row)" />
            </el-tooltip>
            <el-tooltip v-permission="'/classfc/del'" class="item" effect="light" content="删除" placement="top">
              <el-button type="danger" icon="el-icon-delete" size="mini" @click="classfcDel(slot.row.classfcId)" />
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!--弹窗-->
    <div>
      <el-dialog
        :title="title"
        center
        width="40%"
        :visible.sync="dialogVisible"
        :before-close="closeDialog"
      >
        <el-form>
          <el-form-item label="标签名称：" label-width="120px">
            <el-input v-model="classfcInfo.classfcNm" style="width: 80%" />
          </el-form-item>
          <el-form-item label="排序：" label-width="120px">
            <!--            :max="10"-->
            <el-input-number
              v-model="classfcInfo.sort"
              controls-position="right"
              :min="0"
              style="width: 80%"
            />
          </el-form-item>
          <el-form-item label="菜单简介：" label-width="120px">
            <el-input
              v-model="classfcInfo.content"
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

    <div>
      <!--分页-->
      <el-pagination
        :current-page="current"
        :page-size="limit"
        :total="total"
        style="padding: 30px;
        text-align: left"
        layout="total,prev,pager,next,jumper"
        @current-change="getClassfcList"
      />
    </div>

  </div>
</template>

<script>
import tagclassfc from '@/api/meetblog/tagclassfc'

export default {
  name: 'Classfc',
  data() {
    return {
      current: 1, // 当前页
      limit: 10, // 每页记录数
      total: 0, // 总页数
      classfcList: [],
      searchObj: {},
      title: '', // 弹窗标题
      dialogVisible: false, // 弹窗开关标志
      operFlg: '', // 操作标志
      classfcInfo: {}// 分类信息
    }
  },
  created() {
    this.getClassfcList()
  },
  methods: {
    getClassfcList(page = 1) {
      this.searchObj.pageNum = page
      this.searchObj.pageSize = this.limit
      tagclassfc.getClassfcList(this.searchObj).then(res => {
        this.classfcList = res.data.classfcPageInfo.records
        this.total = res.data.classfcPageInfo.totalRow
      })
    },

    /* 打开弹窗添加角色*/
    classfcAdd() {
      this.operFlg = '2'
      this.title = '新增分类'
      this.dialogVisible = true
    },
    /**
     * 关闭弹窗
     */
    closeDialog() {
      this.operFlg = ''
      this.dialogVisible = false
      this.classfcInfo = {}
    },
    /* 新增或者修改*/
    saveOrUpdate() {
      if (this.operFlg == '1') {
        tagclassfc.classfcMdf(this.classfcInfo).then(res => {
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          this.getClassfcList()
          this.closeDialog()
        })
      } else if (this.operFlg == '2') {
        tagclassfc.classfcAdd(this.classfcInfo).then(res => {
          this.$message({
            type: 'success',
            message: '新增成功!'
          })
          this.getClassfcList()
          this.closeDialog()
        })
      }
    },

    classfcMdf(classfcInfo) {
      this.classfcInfo = classfcInfo
      this.operFlg = '1'
      this.title = '修改分类'
      this.dialogVisible = true
    },
    classfcDel(classfcId) {
      this.$confirm('此操作将删除菜单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var obj = {}
        obj.classfcId = classfcId
        tagclassfc.classfcDel(obj).then(res => {
          // 提示
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.getClassfcList()
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
