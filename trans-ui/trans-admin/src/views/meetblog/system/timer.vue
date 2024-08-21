<template>
  <div class="app-container">
    <!--搜索框-->
    <div>
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-input v-model="searobj.jobName" clearable placeholder="任务名称" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="searobj.enumId" clearable placeholder="字典类型" />
        </el-form-item>
        <el-button v-permission="'/role/list'" type="primary" icon="el-icon-search" :loading="loading" @click="jobList()">查询</el-button>
        <el-button v-permission="'/role/add'" type="primary" icon="el-icon-document-add" @click="enumAdd">新增
        </el-button>
        <el-button v-permission="'/role/add'" type="primary" icon="el-icon-upload" @click="batchImp">批量导入
        </el-button>
      </el-form>
    </div>
    <!--数据显示-->
    <div>
      <el-table v-loading="loading" :data="jobListInfo" style="width: 100%">
        <el-table-column fixed type="index" align="center" width="50" label="序号" />
        <el-table-column prop="jobName" align="center" label="任务名称" width="180" />
        <el-table-column prop="concurrent" align="center" label="是否允许并发" width="180" />
        <el-table-column prop="cronType" align="center" label="任务配置方式" width="180" />
        <el-table-column prop="jobStat" align="center" label="任务状态" width="80">
          <template v-slot="scope">
            <el-switch
              v-model="scope.row.jobStat"
              disabled
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="0"
              inactive-value="1"
            />
          </template>
        </el-table-column>
        <!--        <el-table-column prop="startTime" align="center" label="任务开始时间" width="180" />
        <el-table-column prop="endTime" align="center" label="任务结束时间" width="180" />-->
        <el-table-column prop="createTime" align="center" label="创建时间" width="180" />
        <el-table-column prop="updateTime" align="center" label="修改时间" width="180" />
        <el-table-column fixed="right" align="left" label="操作" width="120">
          <template v-slot="scope">
            <el-tooltip v-permission="'/tag/mdf'" class="item" effect="light" content="修改" placement="top">
              <el-button type="primary" icon="el-icon-edit" size="mini" @click="enumMdf(scope.row)" />
            </el-tooltip>
            <el-tooltip v-permission="'/tag/del'" class="item" effect="light" content="删除" placement="top">
              <el-button type="danger" icon="el-icon-delete" size="mini" @click="enumDel(scope.row.id)" />
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
          multiple
        >
          <i class="el-icon-upload" />
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div slot="tip" class="el-upload__tip">只能上传xls/xlsx文件</div>
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
            <el-input v-model="jobInfo.remark" style="width: 80%" />
          </el-form-item>
          <el-form-item label="字典类型：" label-width="120px">
            <el-input v-model="jobInfo.enumId" style="width: 80%" />
          </el-form-item>
          <el-form-item label="排序：" label-width="120px">
            <el-input-number
              v-model="jobInfo.seq"
              controls-position="right"
              :min="0"
              style="width: 80%"
            />
          </el-form-item>
          <el-form-item label="字典键：" label-width="120px">
            <el-input v-model="jobInfo.keyNm" style="width: 80%" />
          </el-form-item>
          <el-form-item label="字典值：" label-width="120px">
            <el-input v-model="jobInfo.keyId" style="width: 80%" />
          </el-form-item>
          <el-form-item label="字典状态：" label-width="120px">
            <el-switch
              v-model="jobInfo.enumStat"
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
        :page-sizes="[10, 20, 30, 50]"
        :total="total"
        style="padding: 30px;
        text-align: left"
        layout="total,sizes,prev,pager,next,jumper"
        @current-change="jobList"
        @size-change="handleSizeChange"
      />
    </div>
  </div>
</template>

<script>

import timerjs from '@/api/meetblog/system/timer'

export default {
  name: 'Tag',
  data() {
    return {
      current: 1, // 当前页
      limit: 10, // 每页记录数
      total: 0, // 总页数
      jobListInfo: [],
      loading: false,
      title: '', // 弹窗标题
      dialogVisible: false, // 弹窗开关标志
      fileTitle: '',
      fileuploadDialog: false,
      operFlg: '', // 操作标志
      jobInfo: {
        seq: 0,
        jobStat: '0'
      }, // 标签信息
      searobj: {}
    }
  },
  created() {
    this.jobList()
  },
  methods: {
    // eslint-disable-next-line vue/no-dupe-keys
    jobList(page = 1) {
      this.loading = true
      this.searobj.pageNum = page
      this.searobj.pageSize = this.limit
      timerjs.getJobList(this.searobj).then(res => {
        debugger
        this.jobListInfo = res.data.records
        this.total = res.data.totalRow
        this.loading = false
      })
    },
    handleSizeChange(val) {
      this.limit = val
      this.jobList()
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
      this.jobInfo = { seq: 0 }
    },
    closeFileDialog() {
      this.fileuploadDialog = false
    },
    /* 新增或者修改*/
    saveOrUpdate() {
      if (this.operFlg === '1') {
        enumjs.enumMdf(this.jobInfo).then(() => {
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          this.jobList()
          this.closeDialog()
        })
      } else if (this.operFlg === '2') {
        enumjs.enumAdd(this.jobInfo).then(() => {
          this.$message({
            type: 'success',
            message: '新增成功!'
          })
          this.jobList()
          this.closeDialog()
        })
      }
    },
    /**
     *
     * @param jobInfo
     */
    enumMdf(jobInfo) {
      this.jobInfo = jobInfo
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
        const obj = {}
        obj.id = id
        enumjs.enumDel(obj).then(() => {
          // 提示
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.jobList()
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
