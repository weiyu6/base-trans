import request from "@/utils/request";

export default {

  getRoleList(sear) {
    return request({
      url: `/online/role/roleList`,
      method: 'post',
      data: sear
    })
  },

  roleInfoAdd(roleInfo) {
    return request({
      url: `/online/role/roleAdd`,
      method: 'post',
      data: roleInfo
    })
  },
  roleInfoQry(obj){
    return request({
      url: `/online/role/roleInfoQry`,
      method: 'post',
      data: obj
    })
  },
  roleInfoMdf(roleInfo) {
    return request({
      url: `/online/role/roleMdf`,
      method: 'post',
      data: roleInfo
    })
  },
  roleInfoDel(obj) {
    return request({
      url: `/online/role/roleDel`,
      method: 'post',
      data: obj
    })
  }
}
