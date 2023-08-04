import request from "@/utils/request";
export default {

  getEnumList(searchObj){
    return request({
      url: `/online/enumlist/enumListQry`,
      method: 'post',
      data: searchObj
    })
  },

}
