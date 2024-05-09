<template>
  <div class="app-container">
    <!--搜索框-->
    <div>
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-input v-model="searobj.remark" clearable placeholder="字典名称"/>
        </el-form-item>
        <el-form-item>
          <el-input v-model="searobj.enumId" clearable placeholder="字典类型"/>
        </el-form-item>
        <el-button v-permission="'/role/list'" type="primary" icon="el-icon-search" @click="enumList()">查询</el-button>
        <el-button v-permission="'/role/add'" type="primary" icon="el-icon-document-add" @click="enumAdd">新增
        </el-button>
        <el-button v-permission="'/role/add'" type="primary" icon="el-icon-document-add" @click="batchImp">批量导入
        </el-button>
      </el-form>
    </div>
    <!--数据显示-->
    <div>
      <el-table :data="enumListInfo" style="width: 100%">
        <el-table-column fixed type="index" align="center" width="50" label="序号"/>
        <el-table-column prop="remark" align="center" label="字典名称" width="180"/>
        <el-table-column prop="enumId" align="center" label="字典类型" width="180"/>
        <el-table-column prop="seq" align="center" label="排序" width="80"/>
        <el-table-column prop="keyNm" align="center" label="字典键" width="120"/>
        <el-table-column prop="keyId" align="center" label="字典值" width="80"/>
        <el-table-column prop="enumStat" align="center" label="字典状态" width="80">
          <template v-slot="scope">
            <el-switch
              v-model="scope.row.enumStat"
              disabled
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="0"
              inactive-value="1"
            />
          </template>
        </el-table-column>
        <el-table-column prop="createTime" align="center" label="创建时间" width="180"/>
        <el-table-column prop="updateTime" align="center" label="修改时间" width="180"/>
        <el-table-column fixed="right" align="left" label="操作" width="120">
          <template v-slot="scope">
            <el-tooltip v-permission="'/tag/mdf'" class="item" effect="light" content="修改" placement="top">
              <el-button type="primary" icon="el-icon-edit" size="mini" @click="enumMdf(scope.row)"/>
            </el-tooltip>
            <el-tooltip v-permission="'/tag/del'" class="item" effect="light" content="删除" placement="top">
              <el-button type="danger" icon="el-icon-delete" size="mini" @click="enumDel(scope.row.id)"/>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!--弹窗-->
    <div>
      <el-dialog
        :title="fileTitle"
        center
        width="40%"
        :visible.sync="fileuploadDialog"
        :before-close="closeFileDialog"
      >
        <el-upload
          class="upload-demo"
          drag
          action="https://jsonplaceholder.typicode.com/posts/"
          multiple>
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip">只能上传xls/xlsx文件</div>
        </el-upload>
      </el-dialog>
    </div>
    <div>
      <el-dialog
        :title="title"
        center
        width="40%"
        :visible.sync="dialogVisible"
        :before-close="closeDialog"
      >
        <el-form>
          <el-form-item label="字典名称：" label-width="120px">
            <el-input v-model="enumInfo.remark" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="字典类型：" label-width="120px">
            <el-input v-model="enumInfo.enumId" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="排序：" label-width="120px">
            <!--            :max="10"-->
            <el-input-number
              v-model="enumInfo.seq"
              controls-position="right"
              :min="0"
              style="width: 80%"
            />
          </el-form-item>
          <el-form-item label="字典键：" label-width="120px">
            <el-input v-model="enumInfo.keyNm" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="字典值：" label-width="120px">
            <el-input v-model="enumInfo.keyId" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="字典状态：" label-width="120px">
            <el-switch
              v-model="enumInfo.enumStat"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="0"
              inactive-value="1"
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
        @current-change="enumList"
      />
    </div>
  </div>
</template>

<script>

import enumjs from '@/api/meetblog/enumlist'

export default {
  name: 'Tag',
  data() {
    return {
      current: 1, // 当前页
      limit: 10, // 每页记录数
      total: 0, // 总页数
      enumListInfo: [],

      title: '', // 弹窗标题
      dialogVisible: false, // 弹窗开关标志
      fileTitle: '',
      fileuploadDialog: false,
      operFlg: '', // 操作标志
      enumInfo: {
        seq: 0,
        enumStat: '0'
      }, // 标签信息
      searobj: {}
    }
  },
  created() {
    this.enumList()
  },
  methods: {
    // eslint-disable-next-line vue/no-dupe-keys
    enumList(page = 1) {
      this.searobj.pageNum = page
      this.searobj.pageSize = this.limit
      enumjs.enumList(this.searobj).then(res => {
        this.enumListInfo = res.data.records
        this.total = res.data.totalRow
      })
    },
    /* 打开弹窗添加角色*/
    enumAdd() {
      this.operFlg = '2'
      this.title = '新增字典'
      this.dialogVisible = true
    },
    batchImp() {
      this.fileuploadDialog = true
      this.fileTitle = '批量导入'
    },
    /**
     * 关闭弹窗
     */
    closeDialog() {
      this.operFlg = ''
      this.dialogVisible = false
      this.enumInfo = {seq: 0}
    },
    closeFileDialog() {
      this.fileuploadDialog = false
    },
    /* 新增或者修改*/
    saveOrUpdate() {
      if (this.operFlg === '1') {
        enumjs.enumMdf(this.enumInfo).then(res => {
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          this.enumList()
          this.closeDialog()
        })
      } else if (this.operFlg === '2') {
        enumjs.enumAdd(this.enumInfo).then(res => {
          this.$message({
            type: 'success',
            message: '新增成功!'
          })
          this.enumList()
          this.closeDialog()
        })
      }
    },
    /**
     *
     * @param enumInfo
     */
    enumMdf(enumInfo) {
      this.enumInfo = enumInfo
      this.operFlg = '1'
      this.title = '修改字典'
      this.dialogVisible = true
    },
    enumDel(id) {
      this.$confirm('此操作将删除菜单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var obj = {}
        obj.id = id
        enumjs.enumDel(obj).then(res => {
          // 提示
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.enumList()
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
.upload-demo{
  text-align: center;
}
</style>
