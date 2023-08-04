import request from "@/utils/request";

export default {

  userInfoList(searchObj){
    return request({
      url: `/online/user/userInfoList`,
      method: 'post',
      data: searchObj
    })
  },
  userInfoById(searchObj){
    return request({
      url: `/online/user/userInfoById`,
      method: 'post',
      data: searchObj
    })
  },
  addUser(searchObj){
    return request({
      url: `/online/user/addUser`,
      method: 'post',
      data: searchObj
    })
  },
  userInfoMdf(searchObj){
    return request({
      url: `/online/user/userInfoMdf`,
      method: 'post',
      data: searchObj
    })
  },

}
