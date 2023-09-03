import { constantRoutes } from '@/router'
import { getToken } from '@/utils/auth'
import Layout from '@/layout'
import menu from '@/api/meetblog/menu'

/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
  const res = []
  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
  })

  return res
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({ commit }, roles) {
    return new Promise(resolve => {
      const loadMenuData = []

      const sear = {}
      sear.token = getToken()
      menu.getMenuTree(sear).then(res => {
        debugger
        let data = []
        data = res.data.info.menuTree
        Object.assign(loadMenuData, data)
        const asyncRoutes = []
        generaMenu(asyncRoutes, data)

        let accessedRoutes
        if (roles.includes('3')) {
          accessedRoutes = asyncRoutes || []
        } else {
          accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
        }
        // eslint-disable-next-line prefer-const
        commit('SET_ROUTES', accessedRoutes)
        // 会将accessedRoutes返回给调用
        resolve(accessedRoutes)
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

export function generaMenu(routes, data) {
  data.forEach(item => {
    // alert(JSON.stringify(item))

    let menu = {
      path: item.path,
      component: item.component === 'Layout' ? Layout : resolve => {
        require(['@/views' + item.component], resolve)
      },
      // hidden: true,
      children: [],
      name: item.menuNm,
      meta: { title: item.menuNm, icon: item.icon }
    }
    if (item.linkFlg !== '0') {
      menu = {
        path: item.path,
        // hidden: true,
        children: [],
        name: item.menuNm,
        meta: { title: item.menuNm, icon: item.icon }
      }
    }
    if (item.children) {
      generaMenu(menu.children, item.children)
    }

    routes.push(menu)
  })
}
