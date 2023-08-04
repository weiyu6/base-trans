import request from "@/utils/request";

export default {

  getTagList(obj) {
    return request({
      url: `/online/tag/tagList`,
      method: 'post',
      data: obj
    })
  },
  tagAdd(tagInfo) {
    return request({
      url: `/online/tag/tagAdd`,
      method: 'post',
      data: tagInfo
    })
  },
  tagMdf(tagInfo) {
    return request({
      url: `/online/tag/tagMdf`,
      method: 'post',
      data: tagInfo
    })
  },
  tagDel(obj) {
    return request({
      url: `/online/tag/tagDel`,
      method: 'post',
      data: obj
    })
  },
  /*分类相关操作*/
  getClassfcList(obj) {
    return request({
      url: `/online/classfc/classfcList`,
      method: 'post',
      data: obj
    })
  },
  classfcMdf(classfcInfo) {
    return request({
      url: `/online/classfc/classfcMdf`,
      method: 'post',
      data: classfcInfo
    })
  },
  classfcAdd(classfcInfo) {
    return request({
      url: `/online/classfc/classfcAdd`,
      method: 'post',
      data: classfcInfo
    })
  },
  classfcDel(obj) {
    return request({
      url: `/online/classfc/classfcDel`,
      method: 'post',
      data: obj
    })
  }
}
