import request from "@/utils/request";

export default {

  fileUpload(searchObj) {
    return request({
      url: `/supp/file/fileUpload`,
      method: 'post',
      data: searchObj
    })
  },
}
