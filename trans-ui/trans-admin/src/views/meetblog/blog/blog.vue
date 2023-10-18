<template>
  <div class="app-container">
    <!--搜索框-->
    <div>
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-input v-model="searobj.title" clearable placeholder="博客标题" style="width: 140px;" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="searobj.author" clearable placeholder="作者" style="width: 140px;" />
        </el-form-item>
        <el-form-item>
          <el-select v-model="searobj.blogType" clearable placeholder="文章类型" style="width: 140px">
            <el-option
              v-for="item in blogTypeList"
              :key="item.enumId+''+item.seq"
              :label="item.keyNm"
              :value="item.keyId"
            />
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-select v-model="searobj.classfcId" clearable filterable placeholder="分类" style="width: 140px">
            <el-option
              v-for="item in classfcList"
              :key="item.classfcId"
              :label="item.classfcNm"
              :value="item.classfcId"
            />
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-select v-model="searobj.tagId" clearable filterable placeholder="标签" style="width: 140px">
            <el-option
              v-for="item in tagList"
              :key="item.tagId"
              :label="item.tagNm"
              :value="item.tagId"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="searobj.openComment" clearable placeholder="评论状态" style="width: 140px">
            <el-option
              v-for="item in openCommentList"
              :key="item.enumId+''+item.seq"
              :label="item.keyNm"
              :value="item.keyId"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="searobj.publishFlg" clearable placeholder="发布状态" style="width: 140px">
            <el-option
              v-for="item in publishFlgList"
              :key="item.enumId+''+item.seq"
              :label="item.keyNm"
              :value="item.keyId"
            />
          </el-select>
        </el-form-item>
        <el-button v-permission="'/blog/list'" type="primary" icon="el-icon-search" @click="getBlogList()">查询</el-button>
      </el-form>

      <el-row :gutter="10" style="margin-bottom: 8px;">
        <el-col :span="1.5">
          <el-button v-permission="'/blog/add'" type="success" icon="el-icon-edit" @click="classfcAdd">新增博客</el-button>
        </el-col>
      </el-row>
    </div>

    <!--数据显示-->
    <div>
      <el-table :data="blogList" style="width: 100%">
        <el-table-column fixed type="index" align="center" width="50" label="序号" />
        <el-table-column prop="blogId" align="center" label="封面" width="120">
          <template v-slot="slot">
            <img :src="slot.row.fileUid" class="avatarInfo">
          </template>
        </el-table-column>
        <el-table-column prop="title" align="center" label="标题" width="150" />
        <el-table-column prop="author" align="center" label="作者" width="120" />
        <el-table-column prop="blogType" align="center" label="文章类型" width="120">
          <template v-slot="slot">
            <el-tag
              v-for="item in blogTypeList"
              v-if="slot.row.blogType === item.keyId"
            >
              {{ item.keyNm }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="classfc" align="center" label="分类" width="120">
          <template v-slot="slot">
            <el-tag
              v-for="item in classfcList"
              v-if="slot.row.classfcId === item.classfcId"
            >
              {{ item.classfcNm }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="classfc" align="center" label="标签" width="160">
          <template v-slot="slot">
            <el-select v-model="slot.row.tagIds" multiple disabled>
              <el-option
                v-for="item in tagList"
                :key="item.tagId"
                :label="item.tagNm"
                :value="item.tagId"
              />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column prop="classfc" align="center" label="评论状态" width="120">
          <template v-slot="slot">
            <el-tag
              v-for="item in openCommentList"
              v-if="slot.row.openComment === item.keyId"
              :type="item.keyId === '0' ?'danger':'success'"
            >
              {{ item.keyNm }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="classfc" align="center" label="发布状态" width="120">
          <template v-slot="slot">
            <el-tag
              v-for="item in publishFlgList"
              v-if="slot.row.publishFlg === item.keyId"
              :type="item.keyId === '0' ?'danger':'success'"
            >
              {{ item.keyNm }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="clickCount" sortable align="center" label="点击数" width="120" />
        <el-table-column prop="createTime" align="center" label="创建时间" width="240">
          <template v-slot="slot">
            <el-date-picker
              v-model="slot.row.createTime"
              disabled
              type="datetime"
            />
          </template>
        </el-table-column>
        <el-table-column align="left" fixed="right" label="操作" width="160">
          <template v-slot="slot">
            <el-tooltip v-permission="'/blog/mdf'" class="item" effect="light" content="修改" placement="top">
              <el-button type="primary" icon="el-icon-edit" size="mini" @click="blogMdf(slot.row)" />
            </el-tooltip>
            <el-tooltip v-permission="'/blog/del'" class="item" effect="light" content="删除" placement="top">
              <el-button type="danger" icon="el-icon-delete" size="mini" @click="blogDel(slot.row.blogId)" />
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
        fullscreen
        :visible.sync="dialogVisible"
        :before-close="closeDialog"
      >
        <el-form>

          <el-row>
            <el-col :span="16">
              <el-form-item label="标题：" :label-width="formLabelWidth" prop="title">
                <el-input v-model="blogInfo.title" />
              </el-form-item>

              <el-form-item label="简介：" :label-width="formLabelWidth">
                <el-input v-model="blogInfo.summy" />
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="封面：" :label-width="formLabelWidth">
                <div class="imgBody">
                  <el-upload
                    v-model="blogInfo.fileUid"
                    class="avatar-uploader"
                    :action="BASE_API + '/supp/file/fileUpload'"
                    :show-file-list="false"
                    :on-success="fileUploadSuccess"
                    :before-upload="beforeAvatarUpload"
                  >
                    <img v-if="imageUrl" :src="imageUrl" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon" />
                  </el-upload>
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6.5">
              <el-form-item label="分类：" :label-width="formLabelWidth">
                <el-select v-model="blogInfo.classfcId" clearable placeholder="分类">
                  <el-option
                    v-for="item in classfcList"
                    :key="item.classfcId"
                    :label="item.classfcNm"
                    :value="item.classfcId"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6.5">
              <el-form-item label="标签：" :label-width="formLabelWidth" prop="title">
                <el-select v-model="blogInfo.tagIds" multiple :multiple-limit="2" placeholder="标签">
                  <el-option
                    v-for="item in tagList"
                    :key="item.tagId"
                    :label="item.tagNm"
                    :value="item.tagId"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6.5">
              <el-form-item label="文章类型：" :label-width="formLabelWidth">
                <el-radio
                  v-for="item in blogTypeList"
                  :key="item.enumId+''+item.seq"
                  v-model="blogInfo.blogType"
                  :label="item.keyId"
                  border
                  size="small"
                >
                  {{ item.keyNm }}
                </el-radio>
              </el-form-item>
            </el-col>

            <el-col :span="6.5">
              <el-form-item label="评论状态：" :label-width="formLabelWidth">
                <el-radio v-model="blogInfo.openComment" size="small" label="1" border>开启</el-radio>
                <el-radio v-model="blogInfo.openComment" size="small" label="0" border>关闭</el-radio>
              </el-form-item>
            </el-col>

            <el-col :span="6.5">
              <el-form-item label="发布状态：" :label-width="formLabelWidth">
                <el-radio v-model="blogInfo.publishFlg" size="small" label="1" border>发布</el-radio>
                <el-radio v-model="blogInfo.publishFlg" size="small" label="0" border>下架</el-radio>
              </el-form-item>
            </el-col>
          </el-row>

          <el-form-item v-if="blogInfo.blogType==1" label="原文链接：" :label-width="formLabelWidth">
            <el-input v-model="blogInfo.articlesPart" auto-complete="off" />
          </el-form-item>

          <el-form-item v-if="blogInfo.blogType == 2" label="外链：" :label-width="formLabelWidth" prop="outsideLink">
            <el-input v-model="blogInfo.outsideLink" auto-complete="off" />
          </el-form-item>

          <el-form-item label="内容：" label-width="120px">
            <wangeditor v-model="blogInfo.content" class="set-wang" :is-clear="isClear" />
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
        @current-change="getBlogList"
      />
    </div>

  </div>
</template>

<script>
import wangeditor from '@/components/WangEditor/wangeditor'
import tagclassfc from '@/api/meetblog/tagclassfc'
import blog from '@/api/meetblog/blog'
import enumlist from '@/api/meetblog/enumlist'

export default {
  name: 'Blog',
  components: {
    wangeditor
  },
  data() {
    return {
      BASE_API: process.env.VUE_APP_BASE_API, // 获取后端接口地址
      isClear: false,
      searobj: {},
      current: 1, // 当前页
      limit: 10, // 每页记录数
      total: 0, // 总页数
      title: '', // 弹窗标题
      dialogVisible: false, // 弹窗开关标志
      operFlg: '', // 操作标志
      blogInfo: {}, // 博客信息
      blogList: [], // 博客列表
      formLabelWidth: '120px',
      lineLabelWidth: '120px', // 一行的间隔数
      icon: false, // 控制删除图标的显示
      tagList: [], // 标签列表
      classfcList: [], // 分类列表
      imageUrl: '',
      enumIdList: {}, // 枚举列表
      blogTypeList: [], // 博客类型集合
      commentStatList: [], // 评论状态集合
      openCommentList: [], // 是否开启评论集合
      publishFlgList: []// 文章发布状态集合
    }
  },
  created() {
    this.getTagList()
    this.getClassfcList()
    this.getEnumList()
    this.getBlogList()
  },
  methods: {

    getBlogList(page = 1) {
      this.searobj.pageNum = page
      this.searobj.pageSize = this.limit
      blog.getBlogList(this.searobj).then(res => {
        this.blogList = res.data.blogExtendPageInfo.records
        this.total = res.data.blogExtendPageInfo.totalPage
      })
    },
    /* 打开弹窗添加角色*/
    classfcAdd() {
      this.operFlg = '2'
      this.title = '新增博客'
      this.dialogVisible = true
    },
    /**
     * 关闭弹窗
     */
    closeDialog() {
      this.operFlg = ''
      this.dialogVisible = false
      this.blogInfo = {}
      this.imageUrl = ''
    },
    /* 新增或者修改*/
    saveOrUpdate() {
      if (this.operFlg == '1') {
        blog.blogMdf(this.blogInfo).then(res => {
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          this.getBlogList()
          this.closeDialog()
        })
      } else if (this.operFlg == '2') {
        blog.blogAdd(this.blogInfo).then(res => {
          this.$message({
            type: 'success',
            message: '新增成功!'
          })
          this.getBlogList()
          this.closeDialog()
        })
      }
    },

    // 上传成功回调
    fileUploadSuccess(response) {
      if (response.code === '000000') {
        this.$message.success('上传成功')
        this.blogInfo.fileUid = response.data.ossFileDir
        this.imageUrl = response.data.ossFileDir
      } else {
        this.$message.error(response.msg)
        this.imageUrl = ''
      }
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },

    getTagList() {
      var obj = {}
      tagclassfc.getTagList(obj).then(res => {
        this.tagList = res.data.tagPageInfo.records
      })
    },
    getClassfcList() {
      var obj = {}
      tagclassfc.getClassfcList(obj).then(res => {
        this.classfcList = res.data.classfcPageInfo.records
      })
    },
    /* 查询出枚举列表*/
    getEnumList() {
      this.enumIdList.enumIds = [{ enumId: 'BLOGTYPE' }, { enumId: 'COMMENT_STAT' },
        { enumId: 'OPEN_COMMENT' }, { enumId: 'PUBLISH_FLG' }]
      enumlist.getEnumList(this.enumIdList).then(res => {
        var enumMap = res.data
        this.blogTypeList = enumMap.BLOGTYPE
        this.commentStatList = enumMap.COMMENT_STAT
        this.openCommentList = enumMap.OPEN_COMMENT
        this.publishFlgList = enumMap.PUBLISH_FLG
      })
    },
    blogMdf(blogInfo) {
      this.operFlg = '1'
      this.title = '修改博客'
      this.blogInfo = blogInfo
      this.imageUrl = this.blogInfo.fileUid
      this.dialogVisible = true
    },
    blogDel(blogId) {
      this.$confirm('此操作将删除博客, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var obj = {}
        obj.blogId = blogId
        blog.blogDel(obj).then(res => {
          // 提示
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.getBlogList()
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

<style>
.imgBody {
  width: 177px;
  height: 100px;
  border: solid 2px #ffffff;
  float: left;
  position: relative;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 177px;
  height: 100px;
  line-height: 100px;
  text-align: center;
}

.avatar {
  width: 177px;
  height: 100px;
  display: block;
}

.avatarInfo {
  width: 90px;
  height: 50px;
  display: block;
}
</style>
