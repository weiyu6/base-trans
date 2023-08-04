import request from "@/utils/request";

export default {

  init(searchObj) {
    return request({
      url: `/online/index/init`,
      method: 'post',
      data: searchObj
    })
  },
}
