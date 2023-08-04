import request from "@/utils/request";

export default {

  imgUpload(formData) {
    return request({
      url: `/supp/file/fileUpload`,
      method: 'post',
      headers: {
        'Content-Type': 'multipart/form-data'
      },
      data: formData
    })
  },
}
