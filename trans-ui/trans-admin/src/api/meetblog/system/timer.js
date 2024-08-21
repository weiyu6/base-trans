import request from '@/utils/request'
export default {

  getJobList(searchObj) {
    return request({
      url: `/online/quartz/jobListQry`,
      method: 'post',
      data: searchObj
    })
  },
  enumList(searchObj) {
    return request({
      url: `/online/enumlist/enumList`,
      method: 'post',
      data: searchObj
    })
  },
  enumAdd(enumInfo) {
    return request({
      url: `/online/enumlist/enumAdd`,
      method: 'post',
      data: enumInfo
    })
  },
  enumMdf(enumInfo) {
    return request({
      url: `/online/enumlist/enumMdf`,
      method: 'post',
      data: enumInfo
    })
  },
  enumDel(enumInfo) {
    return request({
      url: `/online/enumlist/enumDel`,
      method: 'post',
      data: enumInfo
    })
  }

}
