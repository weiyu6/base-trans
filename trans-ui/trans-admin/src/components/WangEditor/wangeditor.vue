<template lang="html">
  <div class="editor">
    <div ref="editor" class="textNeirong">
    </div>
  </div>
</template>

<script>
import E from 'wangeditor'
import hljs from 'highlight.js'
import store from '@/store/';
import uploadImg from "@/api/meetblog/uploadImg";
import 'highlight.js/styles/monokai-sublime.css'
export default {
  name: 'editor',
  data() {
    return {
      // uploadPath,components
      editor: null,
      info_: null,
      token: ''
    }
  },
  model: {
    prop: 'value',
    event: 'change'
  },
  props: {
    value: {
      type: String,
      default: ''
    },
    isClear: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    isClear(val) {
      // 触发清除文本域内容
      if (val) {
        this.editor.txt.clear()
        this.info_ = null
      }
    },
    value: function(value) {
      if (value !== this.editor.txt.html()) {
        this.editor.txt.html(this.value)
      }
    }
    //value为编辑框输入的内容，这里我监听了一下值，当父组件调用得时候，如果给value赋值了，子组件将会显示父组件赋给的值
  },
  created() {
    this.token = 'Bearer ' + store.getters.token;
  },
  mounted() {
    this.seteditor()
    this.editor.txt.html(this.value)
  },
  methods: {
    seteditor() {
      this.editor = new E(this.$refs.editor)
      this.editor.config.uploadImgShowBase64 = false // base 64 存储图片
      /*this.editor.config.uploadImgServer = '/trans/oss/uploadImageOss'// 填写配置服务器端地址
      this.editor.config.uploadImgHeaders = { 'Authorization': this.token }// 自定义 header
      this.editor.config.uploadFileName = 'file' // 后端接受上传文件的参数名
      this.editor.config.uploadImgMaxSize = 8 * 1024 * 1024 // 将图片大小限制为 2M
      this.editor.config.uploadImgMaxLength = 6 // 限制一次最多上传 6 张图片
      this.editor.config.uploadImgTimeout = 3 * 60 * 1000 // 设置超时时间*/
      // 自定义 onchange 触发的延迟时间，默认为 200 ms
      this.editor.config.onchangeTimeout = 1000 // 单位 ms
      // 设置编辑区域高度为 500px
      this.editor.config.height = 500
      this.editor.config.onchange = (html) => {
        this.info_ = html // 绑定当前逐渐地值
        this.$emit('change', this.info_) // 将内容同步到父组件中
      }

      /*自己实现上传图片,将图片上传到阿里云oss服务*/
      this.editor.config.customUploadImg = function (resultFiles, insertImgFn) {
        // resultFiles 是 input 中选中的文件列表
        // insertImgFn 是获取图片 url 后，插入到编辑器的方法
        let formData = new FormData()
        formData.append('file', resultFiles[0])//把除了图片也要上传的数据放入formdata
        console.log("formData",formData)
        uploadImg.imgUpload(formData).then(res=>{
          let imgUrl = res.data.ossFileDir
          insertImgFn(imgUrl)
        })
      }

      // 挂载highlight插件
      this.editor.highlight = hljs
      // 菜单栏提示为下标
      // this.editor.config.menuTooltipPosition = 'down'
      this.editor.config.pasteFilterStyle = false
      // 创建富文本编辑器
      this.editor.create()
      /*this.editor.config.uploadImgHooks = {
        fail: (xhr, editor, result) => {
          // 插入图片失败回调
        },
        success: (xhr, editor, result) => {
          // 图片上传成功回调
        },
        timeout: (xhr, editor) => {
          // 网络超时的回调
        },
        error: (xhr, editor) => {
          // 图片上传错误的回调
        },
        customInsert: (insertImg, result, editor) => {
          //循环插入图片
          let url = result.data.url
          insertImg(url)
        }
      }*/


    }
  }
}
</script>

<style>
.editor {
  width: 100%;
  margin: auto;
  margin-top: 20px;
  position: relative;
}
</style>


