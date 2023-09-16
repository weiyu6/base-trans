<template>
  <div class="app-container">

    <div>
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-input v-model="searchObj.transRecdNum" clearable placeholder="交易流水号" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="searchObj.userNm" clearable placeholder="用户名" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="searchObj.ip" clearable placeholder="IP地址" />
        </el-form-item>
        <el-form-item>
          <el-date-picker
            v-model="searchObj.timeInterval"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          />
        </el-form-item>

        <el-button type="primary" icon="el-icon-search" @click="sysLogListQry()">查询</el-button>
      </el-form>
    </div>

    <div>
      <!--      使用row-style和cell-style调整行高-->
      <el-table :data="sysLogList" style="width: 100%">
        <el-table-column fixed type="index" align="center" width="50" label="序号" />
        <el-table-column prop="methodNm" align="center" label="交易名" width="260" />
        <!--        <el-table-column prop="userId" align="center" label="用户ID" width="180"/>-->
        <el-table-column prop="userNm" align="center" label="用户名" width="120" />
        <el-table-column align="center" label="交易类型" width="120">
          <template v-slot="scope">
            <el-tag
              v-for="item in transType"
              v-if="scope.row.transType === item.keyId"
              :type="item.keyNm=='操作类' ?'warning':'success'"
            >
              {{ item.keyNm }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="os" align="center" label="操作系统" width="120" />
        <el-table-column prop="browser" align="center" label="浏览器" width="180" />
        <el-table-column align="center" label="交易耗时" width="180">
          <template v-slot="scope">
            <el-tag>{{ scope.row.consumTime }} ms</el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" label="交易时间" width="240">
          <template v-slot="scope">
            <el-date-picker
              v-model="scope.row.createTime"
              disabled
              type="datetime"
              placeholder="选择日期时间"
            />
          </template>
        </el-table-column>

        <el-table-column prop="transRecdNum" align="center" label="交易流水号" width="260" />
        <el-table-column fixed="right" align="center" label="操作" width="100">
          <template v-slot="scope">

            <el-tooltip class="item" effect="light" content="详情" placement="top">
              <el-button
                type="primary"
                icon="el-icon-info"
                size="mini"
                @click="sysloginfo(scope.row.logId)"
              />
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div>
      <el-dialog
        center
        title="日志详情"
        :visible.sync="dialogVisible"
        width="60%"
        :before-close="closeDialog"
      >

        <el-form :inline="true" class="demo-form-inline" size="small" disabled>
          <el-form-item label="操作名：" label-width="130px">
            <el-input v-model="sysLogInfo.methodNm" style="width: 260px;" />
          </el-form-item>
          <el-form-item label="交易日期：" label-width="130px">
            <el-date-picker
              v-model="sysLogInfo.createTime"
              style="width: 260px;"
              type="datetime"
              placeholder="选择日期时间"
            />
          </el-form-item>

          <el-form-item label="日志ID：" label-width="130px">
            <el-input v-model="sysLogInfo.logId" style="width: 260px;" />
          </el-form-item>
          <el-form-item label="交易类型：" label-width="130px">
            <!--            <el-input v-model="sysLogInfo.transType" style="width: 260px;"/>-->
            <el-tag
              v-for="item in transType"
              v-if="sysLogInfo.transType === item.keyId"
              style="width: 260px;"
              type="info"
            >
              {{ item.keyNm }}
            </el-tag>
          </el-form-item>

          <el-form-item label="用户名：" label-width="130px">
            <el-input v-model="sysLogInfo.userNm" style="width: 260px;" />
          </el-form-item>
          <el-form-item label="用户ID：" label-width="130px">
            <el-input v-model="sysLogInfo.userId" style="width: 260px;" />
          </el-form-item>

          <el-form-item label="操作系统：" label-width="130px">
            <el-input v-model="sysLogInfo.os" style="width: 260px;" />
          </el-form-item>
          <el-form-item label="浏览器：" label-width="130px">
            <el-input v-model="sysLogInfo.browser" style="width: 260px;" />
          </el-form-item>

          <el-form-item label="交易耗时：" label-width="130px">
            <el-input v-model="sysLogInfo.consumTime" style="width: 260px;" />
          </el-form-item>
          <el-form-item label="IP地址：" label-width="130px">
            <el-input v-model="sysLogInfo.ip" style="width: 260px;" />
          </el-form-item>

          <el-form-item label="请求方法名：" label-width="130px">
            <el-input v-model="sysLogInfo.method" style="width: 260px;" />
          </el-form-item>
          <el-form-item label="请求地址：" label-width="130px">
            <el-input v-model="sysLogInfo.url" style="width: 260px;" />
          </el-form-item>
          <el-form-item label="交易流水：" label-width="130px">
            <el-input v-model="sysLogInfo.transRecdNum" style="width: 260px;" />
          </el-form-item>
          <el-form-item label="请求类路径：" label-width="130px">
            <el-input v-model="sysLogInfo.classPath" type="textarea" autosize style="width: 520px;" />
          </el-form-item>
          <el-form-item label="请求参数：" label-width="130px">
            <el-input v-model="sysLogInfo.params" type="textarea" autosize style="width: 520px;" />
          </el-form-item>

        </el-form>
        <!--        <span slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="closeDialog()">取消</el-button>
                </span>-->
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
        @current-change="sysLogListQry"
      />
    </div>

  </div>
</template>

<script>
import syslog from '@/api/meetblog/syslog'
import transRecd from '@/api/meetblog/transRecd'
import enumlist from '@/api/meetblog/enumlist'

export default {
  name: 'SyslogList',
  data() {
    return {
      current: 1, // 当前页
      limit: 10, // 每页记录数
      total: 0, // 总页数
      searchObj: {},
      dialogVisible: false, // 弹窗开关标志
      sysLogList: [], // 日志列表
      sysLogInfo: '',
      enumIdList: {}, // 枚举列表
      transType: []// 交易类型列表
    }
  },
  created() {
    this.sysLogListQry()
    this.getEnumList()
  },
  methods: {

    /**
     * 日志列表
     * @param page
     */
    sysLogListQry(page = 1) {
      this.searchObj.pageNum = page
      this.searchObj.pageSize = this.limit
      syslog.sysloglist(this.searchObj).then(res => {
        this.sysLogList = res.data.list
        this.total = res.data.total
      })
    },
    /* 查询出枚举列表*/
    getEnumList() {
      this.enumIdList.enumIds = [{ enumId: 'TRANS_TYPE' }]
      enumlist.getEnumList(this.enumIdList).then(res => {
        var enumMap = res.data
        this.transType = enumMap.TRANS_TYPE
      })
    },
    /**
     * 根据日志id查询日志详情
     * @param logId
     */
    sysloginfo(logId) {
      var sysreq = {}
      sysreq.logId = logId
      syslog.sysloginfo(sysreq).then(res => {
        this.sysLogInfo = res.data
        this.dialogVisible = true
      })
    },
    /**
     * 关闭弹窗
     */
    closeDialog() {
      this.dialogVisible = false
      this.sysLogInfo = {}
    }
  }
}
</script>

<style scoped>

</style>
