<template>
  <div class="app-container">
    <!--查询模块-->
    <div>
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-input v-model="searchObj.userNm" clearable placeholder="用户名" />
        </el-form-item>
        <el-form-item>
          <el-select v-model="searchObj.userTag" clearable placeholder="用户标签" style="width:140px">
            <el-option
              v-for="item in user_tag"
              :key="item.enumId+''+item.seq"
              :label="item.keyNm"
              :value="item.keyId"
            />
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-select v-model="searchObj.commentStat" clearable placeholder="评论状态" style="width:140px">
            <el-option
              v-for="item in comment_stat"
              :key="item.enumId+''+item.seq"
              :label="item.keyNm"
              :value="item.keyId"
            />
          </el-select>
        </el-form-item>

        <el-button v-permission="'/user/list'" type="primary" icon="el-icon-search" @click="userInfoList()">查询
        </el-button>
        <el-button v-permission="'/user/add'" type="primary" icon="el-icon-document-add" @click="openDialog">添加
        </el-button>
      </el-form>
    </div>
    <!-- 数据显示模块   -->
    <div>
      <!--      使用row-style和cell-style调整行高-->
      <el-table
        :data="userList"
        style="width: 100%"
        :row-style="{height: '0'}"
        :cell-style="{padding: '0'}"
      >
        <el-table-column fixed type="index" align="center" width="50" label="序号" />
        <el-table-column align="center" label="头像" width="100">
          <template slot-scope="scope">
            <img
              :src="scope.row.avatar"
              onerror="onerror=null;src=defaultAvatar"
              style="width: 50px;height: 50px;"
            >
          </template>
        </el-table-column>
        <el-table-column prop="userNm" align="center" label="用户名" width="100" />
        <el-table-column prop="nickNm" align="center" label="昵称" width="100" />
        <el-table-column label="性别" align="center" width="80">
          <!--        <template slot-scope="scope">-->
          <!--          {{ scope.row.gender === '1' ? '男' : '女' }}-->
          <!--        </template>-->
          <template slot-scope="scope">
            <el-tag
              v-for="item in gender"
              v-if="scope.row.gender === item.keyId"
              :key="item.enumId+''+item.seq"
            >
              {{ item.keyNm }}
            </el-tag>
          </template>
        </el-table-column>
        <!--        <el-table-column prop="source" align="center" label="资料来源" width="160"/>-->
        <el-table-column align="center" label="用户标签" width="160">
          <template slot-scope="scope">
            <el-tag
              v-for="item in user_tag"
              v-if="scope.row.userTag === item.keyId"
            >
              {{ item.keyNm }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" label="用户角色" width="190">
          <template slot-scope="scope">
            <el-select v-model="scope.row.roleIds" multiple placeholder="请选择" disabled>
              <el-option
                v-for="item in roleList"
                :key="item.roleId"
                :label="item.roleNm"
                :value="item.roleId"
              />
            </el-select>
          </template>
        </el-table-column>

        <!--        <el-table-column prop="birthday" align="center" label="出生日期" width="160" :formatter="formdt"/>
                <el-table-column prop="email" align="center" label="邮箱" width="160"/>
                <el-table-column prop="mobile" align="center" label="手机号" width="160"/>
                <el-table-column prop="qqNum" align="center" label="QQ号" width="160"/>
                <el-table-column prop="wechatNum" align="center" label="微信号" width="160"/>-->
        <el-table-column align="center" label="评论状态" width="160">
          <template slot-scope="scope">
            <el-tag
              v-for="item in comment_stat"
              v-if="scope.row.commentStat === item.keyId"
              :key="item.enumId+''+item.seq"
            >
              {{ item.keyNm }}
            </el-tag>
          </template>
        </el-table-column>
        <!--        <el-table-column prop="os" align="center" label="操作系统" width="160"/>-->
        <el-table-column align="center" label="是否开启邮件通知" width="160">
          <template slot-scope="scope">
            <el-tag
              v-for="item in start_email_notice"
              v-if="scope.row.startEmailNotice === item.keyId"
              :key="item.enumId+''+item.seq"
            >
              {{ item.keyNm }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" label="是否通过加载校验" width="160">
          <template slot-scope="scope">
            <el-tag
              v-for="item in start_email_notice"
              v-if="scope.row.loadingValid === item.keyId"
              :key="item.enumId+''+item.seq"
            >
              {{ item.keyNm }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="loginCount" align="center" label="登录次数" width="160" />
        <el-table-column prop="lastLoginTime" align="center" label="最后登录时间" width="160" :formatter="formtm" />
        <el-table-column prop="lastLoginIp" align="center" label="最后登录IP" width="160" />
        <el-table-column fixed="right" align="center" label="操作" width="160">
          <template slot-scope="scope">
            <el-button
              v-permission="'/user/mdf'"
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="userByIdQry(scope.row.userId)"
            />
            <el-tooltip class="item" effect="light" content="详情" placement="top">
              <el-button
                v-permission="'/user/info'"
                type="primary"
                icon="el-icon-info"
                size="mini"
                @click="userinfo(scope.row.userId)"
              />
            </el-tooltip>
          </template>

        </el-table-column>

      </el-table>
    </div>

    <div>
      <el-dialog
        center
        title="用户信息"
        :visible.sync="dialogVisible"
        width="60%"
        :before-close="closeDialog"
      >

        <el-form :inline="true" class="demo-form-inline" size="small" :disabled="disabledflg">
          <el-form-item v-show="operFlg==='4'" label="用户ID：" label-width="130px">
            <el-input v-model="userinfoSig.userId" style="width: 260px" />
          </el-form-item>
          <el-form-item label="用户名：" label-width="130px">
            <el-input v-model="userinfoSig.userNm" style="width: 260px" />
          </el-form-item>

          <el-form-item v-show="operFlg=='2'" label="密码：" label-width="130px">
            <el-input v-model="userinfoSig.passWord" style="width: 260px" />
          </el-form-item>
          <el-form-item label="昵称：" label-width="130px">
            <el-input v-model="userinfoSig.nickNm" style="width: 260px" />
          </el-form-item>

          <el-form-item label="性别：" label-width="130px">
            <!--            <el-input v-model="userinfoSig.gender"/>-->
            <el-select v-model="userinfoSig.gender" clearable placeholder="性别" style="width:260px">
              <el-option
                v-for="item in gender"
                :key="item.enumId+''+item.seq"
                :label="item.keyNm"
                :value="item.keyId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="出生日期：" label-width="130px">
            <!--            <el-input v-model="userinfoSig.birthday"/>-->
            <el-date-picker
              v-model="userinfoSig.birthday"
              style="width: 260px;"
              type="date"
              placeholder="选择日期时间"
            />
          </el-form-item>
          <el-form-item label="邮箱：" label-width="130px">
            <el-input v-model="userinfoSig.email" style="width: 260px" />
          </el-form-item>

          <el-form-item label="手机号：" label-width="130px">
            <el-input v-model="userinfoSig.mobile" style="width: 260px" />
          </el-form-item>
          <el-form-item label="QQ号：" label-width="130px">
            <el-input v-model="userinfoSig.qqNum" style="width: 260px" />
          </el-form-item>
          <el-form-item label="微信号：" label-width="130px">
            <el-input v-model="userinfoSig.wechatNum" style="width: 260px" />
          </el-form-item>
          <el-form-item label="职业：" label-width="130px">
            <!--            <el-input style="width: 260px" v-model="userinfoSig.profession"/>-->
            <el-select
              v-model="userinfoSig.profession"
              clearable
              placeholder="职业"
              style="width:260px"
            >
              <el-option
                v-for="item in profession_list"
                :key="item.enumId+''+item.seq"
                :label="item.keyNm"
                :value="item.keyId"
              />
            </el-select>
          </el-form-item>

          <!-- 只有后台注册修改的时候，才显示用户标签-->
          <el-form-item v-if="operFlg!='3'" label="用户标签：" label-width="130px">
            <!-- <el-input v-model="userinfoSig.userTag"/>-->
            <el-select
              v-model="userinfoSig.userTag"
              clearable
              placeholder="用户标签"
              style="width:260px"
            >
              <el-option
                v-for="item in user_tag"
                :key="item.enumId+''+item.seq"
                :label="item.keyNm"
                :value="item.keyId"
              />
            </el-select>
          </el-form-item>
          <el-form-item v-if="operFlg!='3'" label="用户角色：" label-width="130px">
            <!-- <el-input v-model="userinfoSig.userTag"/>-->
            <!--            <el-select clearable placeholder="用户权限" style="width:260px"
                                   v-model="userinfoSig.roleId">
                          <el-option
                            :key="item.enumId+''+item.seq"
                            :label="item.keyNm"
                            :value="item.keyId"
                            v-for="item in user_role"
                          />
                        </el-select>-->

            <el-select v-model="userinfoSig.roleIds" multiple placeholder="用户角色" style="width:260px">
              <el-option
                v-for="item in roleList"
                :key="item.roleId"
                :label="item.roleNm"
                :value="item.roleId"
              />
            </el-select>
          </el-form-item>
          <el-form-item v-if="operFlg!='3'" label="评论状态：" label-width="130px">
            <!-- <el-input v-model="userinfoSig.userTag"/>-->
            <el-select
              v-model="userinfoSig.commentStat"
              clearable
              placeholder="用户标签"
              style="width:260px"
            >
              <el-option
                v-for="item in comment_stat"
                :key="item.enumId+''+item.seq"
                :label="item.keyNm"
                :value="item.keyId"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="头像：" label-width="130px">
            <el-upload
              v-model="userinfoSig.avatar"
              :auto-upload="true"
              :multiple="false"
              :limit="1"
              :on-exceed="fileUploadExceed"
              :on-success="fileUploadSuccess"
              :on-error="fileUploadError"
              :action="BASE_API + '/supp/file/fileUpload'"
              :file-list="fileList"
              accept="image/png, image/jpeg"
            >
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
          </el-form-item>
          <el-form-item label="邮件通知：" label-width="110px">
            <el-switch
              v-model="userinfoSig.startEmailNotice"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="1"
              inactive-value="0"
            />
          </el-form-item>
          <el-form-item label="自我简介：" label-width="130px">
            <el-input
              v-model="userinfoSig.summy"
              style="width: 520px"
              type="textarea"
              placeholder="请输入内容"
              maxlength="150"
              show-word-limit
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
        @current-change="userInfoList"
      />
    </div>

  </div>
</template>

<script>
import userInfo from '@/api/meetblog/userInfo'
import enumlist from '@/api/meetblog/enumlist'
import role from '@/api/meetblog/role'

export default {
  name: 'UserList',
  data() {
    return {
      current: 1, // 当前页
      limit: 10, // 每页记录数
      total: 0, // 总页数
      searchObj: {},
      userList: [], // 用户列表
      enumIdList: {}, // 枚举列表
      gender: [], // 性别列表
      user_tag: [], // 用户标签列表
      user_role: [], // 用户标签列表
      // userTag: '',
      start_email_notice: [], // 是否开启邮件通知
      loading_valid: [], // 是否通过加载校验
      comment_stat: [], // 评论状态列表
      profession_list: [], // 职业列表
      userinfoSig: {}, // 单挑用户信息
      dialogVisible: false, // 弹窗开关标志
      operFlg: '', // 操作标志：1-修改，2-添加,3-web注册,4-查看详情
      BASE_API: process.env.VUE_APP_BASE_API, // 获取后端接口地址
      fileList: [],
      disabledflg: false,

      roleList: []// 角色列表
    }
  },
  computed: {},
  created() {
    this.userInfoList()
    this.getEnumList()
    this.getRoleList()
  },
  methods: {
    /* 查询用户列表*/
    userInfoList(page = 1) {
      this.searchObj.pageNum = page
      this.searchObj.pageSize = this.limit
      userInfo.userInfoList(this.searchObj).then(res => {
        console.log('userres', res)
        this.userList = res.data.userExtendPageInfo.list
        this.total = res.data.userExtendPageInfo.total
      })
    },

    /* 查询出枚举列表*/
    getEnumList() {
      this.enumIdList.enumIds = [{ enumId: 'GENDER' }, { enumId: 'USER_TAG' }, { enumId: 'START_EMAIL_NOTICE' },
        { enumId: 'LOADING_VALID' }, { enumId: 'COMMENT_STAT' }, { enumId: 'PROFESSION' }, { enumId: 'USER_ROLE' }]
      enumlist.getEnumList(this.enumIdList).then(res => {
        var enumMap = res.data
        this.gender = enumMap.GENDER
        this.user_tag = enumMap.USER_TAG
        this.start_email_notice = enumMap.START_EMAIL_NOTICE
        this.loading_valid = enumMap.LOADING_VALID
        this.comment_stat = enumMap.COMMENT_STAT
        this.profession_list = enumMap.PROFESSION
        this.user_role = enumMap.USER_ROLE
      })
    },
    /* 根据id查询用户信息*/
    userByIdQry(id) {
      var userQry = {}
      this.operFlg = '1'
      userQry.userId = id
      userInfo.userInfoById(userQry).then(res => {
        this.userinfoSig = res.data
        this.dialogVisible = true
      })
    },
    /* 查看用户信息详情*/
    userinfo(id) {
      this.operFlg = '4'
      this.disabledflg = true
      var userQry = {}
      userQry.userId = id
      userInfo.userInfoById(userQry).then(res => {
        this.userinfoSig = res.data
        this.dialogVisible = true
      })
    },
    /**
     * 关闭弹窗
     */
    closeDialog() {
      this.dialogVisible = false
      this.disabledflg = false
      this.userinfoSig = {}
      this.fileList = []
    },
    /* 打开弹窗添加用户*/
    openDialog() {
      this.dialogVisible = true
      this.disabledflg = false
      // 后管添加用户
      this.operFlg = '2'
    },
    saveOrUpdate() {
      debugger
      this.userinfoSig.operFlg = this.operFlg

      if (this.operFlg === '1') {
        userInfo.userInfoMdf(this.userinfoSig).then(res => {
          // 提示
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          this.dialogVisible = false
          this.userinfoSig = {}
          // 刷新页面
          this.userInfoList()
          this.current = '1'
        })
      } else if (this.operFlg === '2') {
        userInfo.addUser(this.userinfoSig).then(res => {
          // 提示
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          this.dialogVisible = false
          this.userinfoSig = {}
          // 刷新页面
          this.userInfoList()
        })
      }
    },
    handleClick(row) {
      console.log(row)
    },
    // 上传多于一个文件时
    fileUploadExceed() {
      this.$message.warning('只能选取一个文件')
    },

    // 上传成功回调
    fileUploadSuccess(response) {
      debugger
      if (response.code === '000000') {
        this.$message.success('上传成功')
        this.userinfoSig.avatar = response.data.ossFileDir
      } else {
        this.$message.error(response.msg)
        this.fileList = []
      }
    },

    // 上传失败回调
    fileUploadError(error) {
      this.$message.error('上传失败')
    },

    /* 查询角色列表*/
    getRoleList() {
      var sear = {}
      role.getRoleList(sear).then(res => {
        this.roleList = res.data.roleList
      })
    },

    formdt(row, column) {
      const data = row[column.property]
      const t = new Date(data)// row 表示一行数据, updateTime 表示要格式化的字段名称
      var year = t.getFullYear()
      var month = t.getMonth() + 1
      var day = t.getDate()
      var newTime = year + '-' +
        (month < 10 ? '0' + month : month) + '-' +
        (day < 10 ? '0' + day : day)
      return newTime
    },
    formtm(row, column) {
      const data = row[column.property]
      const t = new Date(data)// row 表示一行数据, updateTime 表示要格式化的字段名称
      var year = t.getFullYear()
      var month = t.getMonth() + 1
      var day = t.getDate()
      var hour = t.getHours()
      var min = t.getMinutes()
      var sec = t.getSeconds()
      var newTime = year + '-' +
        (month < 10 ? '0' + month : month) + '-' +
        (day < 10 ? '0' + day : day) + ' ' +
        (hour < 10 ? '0' + hour : hour) + ':' +
        (min < 10 ? '0' + min : min) + ':' +
        (sec < 10 ? '0' + sec : sec)
      return newTime
    }
  }
}
</script>
<style scoped>

</style>
