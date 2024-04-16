import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'
import { sm2 } from 'sm-crypto'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 60000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent

    if (store.getters.token) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      config.headers.tokenHeader = getToken()
      config.headers.Chnl = 'BLOG_ADMIN'
    }

    const encryptData = sm2.doEncrypt(JSON.stringify(config.data), '046279C92F5A243DFD7E351AAF0E5D754D3BED9890A52F3A9518580640A682E3AC53C5F26F780BE7D00D4EABD812139322CC8CBED48BCE77061A610A58996DD66B')
    config.data = {
      encryptData: encryptData
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
   */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    // const res = JSON.parse(sm2.doDecrypt(response.data, '40B3B68049779D092E8B0071878DD6EAF990842AC22E76A4497BD23BA9561D28'))
    let res = response.data
    if (res.encryptData != null) {
      res = JSON.parse(sm2.doDecrypt(res.encryptData, '40B3B68049779D092E8B0071878DD6EAF990842AC22E76A4497BD23BA9561D28'))
    }
    // if the custom code is not 20000, it is judged as an error.
    if (res.code !== '000000') {
      // 200000：token验证未通过
      if (res.code === '200000') {
        // to re-login
        MessageBox.confirm(res.msg, '确认注销', {
          confirmButtonText: '重新登录',
          showCancelButton: false, // 是否显示取消按钮
          closeOnClickModal: false, // 是否点击遮罩（点击空白处）关闭
          showClose: false, // 是否显示右上角的x
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        })
      } else if (res.code === '300000') {
        // 接口没有权限访问时
        Message({
          message: res.data,
          type: 'error',
          duration: 5 * 1000
        })
        return Promise.reject('error')
      } else {
        Message({
          message: res.msg || 'Error',
          type: 'error',
          duration: 5 * 1000
        })
        return Promise.reject(new Error(res.message || 'Error'))
      }
    } else {
      return res
    }
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
