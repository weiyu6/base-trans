import request from "@/utils/request";

export default {

  getMenuTree(searchObj) {
    return request({
      url: `/online/menu/menutree`,
      method: 'post',
      data: searchObj
    })
  },
  getMenuList(searchObj) {
    return request({
      url: `/online/menu/menuList`,
      method: 'post',
      data: searchObj
    })
  },

  menuAdd(menuForm) {
    return request({
      url: `/online/menu/menuAdd`,
      method: 'post',
      data: menuForm
    })
  },
  menuInfoQry(menuId) {
    return request({
      url: `/online/menu/menuInfoQry`,
      method: 'post',
      data: menuId
    })
  },
  menuMdf(menuForm) {
    return request({
      url: `/online/menu/menuMdf`,
      method: 'post',
      data: menuForm
    })
  },
  menuDelById(menuId) {
    return request({
      url: `/online/menu/menuDel`,
      method: 'post',
      data: menuId
    })
  },
  getButtonTree(sear) {
    return request({
      url: `/online/menu/buttonTree`,
      method: 'post',
      data: sear
    })
  },
  buttonAdd(buttonForm) {
    return request({
      url: `/online/menu/buttonAdd`,
      method: 'post',
      data: buttonForm
    })
  },
  buttonMdf(buttonForm) {
    return request({
      url: `/online/menu/buttonMdf`,
      method: 'post',
      data: buttonForm
    })
  },
  getMenuButtonTree(sear) {
    return request({
      url: `/online/menu/menuButtonTree`,
      method: 'post',
      data: sear
    })
  }
}
