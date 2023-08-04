<template>
  <div class="app-container">

    <div>
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-input v-model="searchObj.userNm" clearable placeholder="用户名"/>
        </el-form-item>
        <el-form-item>
          <el-input v-model="searchObj.ip" clearable placeholder="IP地址"/>
        </el-form-item>
        <el-form-item>
          <el-date-picker
            v-model="searchObj.timeInterval"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>

        <el-button type="primary" icon="el-icon-search" @click="transRecdListQry()">查询</el-button>
      </el-form>
    </div>

    <div>
      <!--      使用row-style和cell-style调整行高-->
      <el-table :data="transRecdList" style="width: 100%">
        <el-table-column fixed type="index" align="center" width="50" label="序号"/>
        <el-table-column prop="transRecdNum" align="center" label="交易流水号" width="260"/>
        <el-table-column prop="method" align="center" label="方法名" width="160"/>
        <!--        <el-table-column prop="urlNm" align="center" label="交易名称" width="160"/>-->
        <el-table-column prop="url" align="center" label="请求地址" width="230"/>
<!--        <el-table-column prop="reqType" align="center" label="请求方式" width="150"/>
        <el-table-column prop="userName" align="center" label="用户名" width="160"/>
        <el-table-column prop="browser" align="center" label="浏览器" width="160"/>
        <el-table-column prop="os" align="center" label="操作系统" width="160"/>
        <el-table-column prop="ipAddr" align="center" label="IP地址" width="160"/>-->
        <el-table-column align="center" label="交易耗时:ms" width="160">
          <template slot-scope="scope">
            <el-tag>{{ scope.row.consumTime}} ms </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="transStatus" align="center" label="交易状态" width="120">
          <template slot-scope="scope">
            <el-tag v-for="item in transStatus" :type="(item.keyNm == '交易成功' ? 'success' : (item.keyNm == '交易处理中' ? 'info' : 'danger'))"
                    v-if="scope.row.transStatus === item.keyId">
              {{ item.keyNm }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="transDate" align="center" label="交易时间" width="160" :formatter="formtm"/>

        <el-table-column fixed="right" align="center" label="操作" width="100">
          <template slot-scope="scope">

            <el-tooltip class="item" effect="light" content="详情" placement="top">
              <el-button type="primary" icon="el-icon-info" size="mini"
                         @click="transrecdinfo(scope.row.transRecdNum)"/>
            </el-tooltip>
          </template>
        </el-table-column>

      </el-table>
    </div>
    <div>
      <el-dialog
        center
        title="交易记录详情"
        :visible.sync="dialogVisible"
        width="60%"
        :before-close="closeDialog">

        <el-form :inline="true" class="demo-form-inline" size="small" disabled>
          <el-form-item label="交易流水：" label-width="130px">
            <el-input v-model="transRecdInfo.transRecdNum" style="width: 260px;"/>
          </el-form-item>
          <el-form-item label="交易日期：" label-width="130px">
            <el-date-picker
              style="width: 260px;"
              v-model="transRecdInfo.transDate"
              type="datetime"
              placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>

          <el-form-item label="用户名：" label-width="130px">
            <el-input v-model="transRecdInfo.userName" style="width: 260px;"/>
          </el-form-item>
          <el-form-item label="用户ID：" label-width="130px">
            <el-input v-model="transRecdInfo.userId" style="width: 260px;"/>
          </el-form-item>

          <el-form-item label="操作系统：" label-width="130px">
            <el-input v-model="transRecdInfo.os" style="width: 260px;"/>
          </el-form-item>
          <el-form-item label="浏览器：" label-width="130px">
            <el-input v-model="transRecdInfo.browser" style="width: 260px;"/>
          </el-form-item>

          <el-form-item label="交易状态：" label-width="130px">
<!--            <el-input v-model="transRecdInfo.transStatus" style="width: 260px;"/>-->
            <el-tag v-for="item in transStatus" style="width: 260px;" type="info"
                    v-if="transRecdInfo.transStatus === item.keyId">
              {{ item.keyNm }}
            </el-tag>
          </el-form-item>
          <el-form-item label="交易耗时：" label-width="130px">
            <el-input v-model="transRecdInfo.consumTime" style="width: 260px;"/>
          </el-form-item>

          <el-form-item label="请求方法名：" label-width="130px">
            <el-input v-model="transRecdInfo.method" style="width: 260px;"/>
          </el-form-item>
          <el-form-item label="请求地址：" label-width="130px">
            <el-input v-model="transRecdInfo.url" style="width: 260px;"/>
          </el-form-item>

          <el-form-item label="IP地址：" label-width="130px">
            <el-input v-model="transRecdInfo.ipAddr" style="width: 260px;"/>
          </el-form-item>
          <el-form-item label="IP地址详情：" label-width="130px">
            <el-input v-model="transRecdInfo.ipSrc" style="width: 260px;"/>
          </el-form-item>

          <el-form-item label="渠道：" label-width="130px">
            <el-input v-model="transRecdInfo.chnl" style="width: 260px;"/>
          </el-form-item>
          <el-form-item label="请求方式：" label-width="130px">
            <el-input v-model="transRecdInfo.reqType" style="width: 260px;"/>
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
        @current-change="transRecdListQry"
      />
    </div>

  </div>

</template>

<script>
import enumlist from "@/api/meetblog/enumlist";
import Cookies from "js-cookie";
import transRecd from "@/api/meetblog/transRecd";

export default {
  name: "transRecdList",
  data() {
    return {
      current: 1, // 当前页
      limit: 10, // 每页记录数
      total: 0, // 总页数
      searchObj: {},
      transRecdList: [],// 用户列表
      transStatus: [],// 交易状态列表
      enumIdList: {},// 枚举列表
      dialogVisible: false,// 弹窗开关标志
      transRecdInfo: '',
    }
  },
  created() {

    this.transRecdListQry()
    this.getEnumList()
  },
  methods: {
    /*查询用户列表*/
    transRecdListQry(page = 1) {
      /*根据登录用户权限，选择是否显示编辑按钮*/
      this.searchObj.pageNum = page
      this.searchObj.pageSize = this.limit
      transRecd.transRecdList(this.searchObj).then(res => {
        this.transRecdList = res.data.list
        this.total = res.data.total
      })
    },
    /*查询出枚举列表*/
    getEnumList() {
      this.enumIdList.enumIds = [{enumId: 'TRANS_STATUS'}]
      enumlist.getEnumList(this.enumIdList).then(res => {

        var enumMap = res.data;
        this.transStatus = enumMap.TRANS_STATUS
      })
    },
    /**
     * 关闭弹窗
     */
    closeDialog() {
      this.dialogVisible = false
      this.userinfoSig = {}
    },
    /**
     * 根据流水号查询详细信息
     * @param transRecdNum
     */
    transrecdinfo(transRecdNum) {
      var transinfo={}
      transinfo.transRecdNum = transRecdNum
      transRecd.transRecdInfo(transinfo).then(res => {
        this.transRecdInfo = res.data
        this.dialogVisible = true
      })
    },


    formtm(row, column) {
      let data = row[column.property]
      let t = new Date(data)//row 表示一行数据, updateTime 表示要格式化的字段名称
      var year = t.getFullYear(),
        month = t.getMonth() + 1,
        day = t.getDate(),
        hour = t.getHours(),
        min = t.getMinutes(),
        sec = t.getSeconds();
      var newTime = year + '-' +
        (month < 10 ? '0' + month : month) + '-' +
        (day < 10 ? '0' + day : day) + ' ' +
        (hour < 10 ? '0' + hour : hour) + ':' +
        (min < 10 ? '0' + min : min) + ':' +
        (sec < 10 ? '0' + sec : sec);
      return newTime;
    },
  }
}
</script>

<style scoped>

</style>
