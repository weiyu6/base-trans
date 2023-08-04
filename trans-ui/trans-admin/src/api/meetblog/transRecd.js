import request from "@/utils/request";

export default {
  transRecdList(searchObj) {
    return request({
      url: `/online/transRecord/recordList`,
      method: 'post',
      data: searchObj
    })
  },

  transRecdInfo(searchObj) {
    return request({
      url: `/online/transRecord/recordInfo`,
      method: 'post',
      data: searchObj
    })
  },
}
