import request from "@/utils/request";

export default {

  getBlogList(searchObj) {
    return request({
      url: `/online/blog/blogList`,
      method: 'post',
      data: searchObj
    })
  },

  blogAdd(blogInfo) {
    return request({
      url: `/online/blog/blogAdd`,
      method: 'post',
      data: blogInfo
    })
  },
  blogMdf(blogInfo) {
    return request({
      url: `/online/blog/blogMdf`,
      method: 'post',
      data: blogInfo
    })
  },
  blogDel(obj) {
    return request({
      url: `/online/blog/blogDel`,
      method: 'post',
      data: obj
    })
  }
}
