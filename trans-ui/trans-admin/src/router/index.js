import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    // 当设置 true 的时候该路由不会在侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
    hidden: true,
    name: '首页',
    children: [{
      path: 'dashboard',
      // name: 'dashboard',// name为空就不会显示在标签栏导航
      component: () => import('@/views/dashboard/index'),
      // meta: {title: '仪表盘', icon: 'dashboard'}
    }]
  },

]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  {
    path: '/blog',
    component: Layout,
    redirect: '/blog/blog',
    name: '博客管理',
    alwaysShow: true,
    meta: {title: '博客管理', icon: 'form'},
    children: [
      {
        path: 'blog',
        name: '博客',
        component: () => import('@/views/meetblog/blog/blog'),
        meta: {title: '博客', icon: 'el-icon-reading'}
      },
      {
        path: 'classfc',
        name: '分类',
        component: () => import('@/views/meetblog/blog/classfc'),
        meta: {title: '分类', icon: 'el-icon-reading'}
      },
      {
        path: 'tag',
        name: '标签',
        component: () => import('@/views/meetblog/blog/tag'),
        meta: {title: '标签', icon: 'el-icon-reading'}
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    redirect: '/user/userList',
    name: '用户管理',
    alwaysShow: true,
    meta: {title: '用户管理', icon: 'form'},
    children: [
      {
        path: 'userList',
        name: '用户列表',
        component: () => import('@/views/meetblog/user/userList'),
        meta: {title: '用户列表', icon: 'el-icon-user'}
      }
    ]
  },
  {
    path: '/auth',
    component: Layout,
    redirect: '/auth/menuList',
    name: '权限管理',
    alwaysShow: true,
    meta: {title: '权限管理', icon: 'form'},
    children: [
      {
        path: 'role',
        name: '角色管理',
        component: () => import('@/views/meetblog/auth/role'),
        meta: {title: '角色管理', icon: 'el-icon-user'}
      },
      {
        path: 'menuList',
        name: '菜单管理',
        component: () => import('@/views/meetblog/auth/menuList'),
        meta: {title: '菜单管理', icon: 'el-icon-user'}
      },
      {
        path: 'buttonList',
        name: '按钮管理',
        component: () => import('@/views/meetblog/auth/buttonList'),
        meta: {title: '菜单管理', icon: 'el-icon-user'}
      }
    ]
  },
  {
    path: '/transinfo',
    component: Layout,
    redirect: '/transinfo/syslogList',
    name: '操作信息',
    alwaysShow: true,
    meta: {title: '操作信息', icon: 'el-icon-notebook-1'},
    children: [
      {
        path: 'transRecdList',
        name: '交易记录',
        component: () => import('@/views/meetblog/transinfo/transRecdList'),
        meta: {title: '交易记录列表', icon: 'el-icon-notebook-2'}
      },
      {
        path: 'syslogList',
        name: '日志列表',
        component: () => import('@/views/meetblog/transinfo/syslogList'),
        meta: {title: '日志列表', icon: 'el-icon-s-data'}
      }
    ]
  },
  {
    path: '/monitor',
    component: Layout,
    redirect: '/monitor/nacos',
    name: '监控中心',
    alwaysShow: true,
    meta: {title: '监控中心', icon: 'el-icon-monitor'},
    children: [
      {
        path: 'http://192.168.111.10:8848/nacos/#',
        name: 'Nacos',
        meta: {title: 'Nacos', icon: 'el-icon-sell'}
      },
      {
        path: 'http://192.168.111.10:9100/#/dashboard',
        name: 'Sentinel',
        meta: {title: 'Sentinel', icon: 'el-icon-box'}
      },
      {
        path: 'http://192.168.111.10:9411/zipkin/',
        name: 'zipkin',
        meta: {title: 'zipkin', icon: 'el-icon-bank-card'}
      },
    ]
  },
  {
    path: 'external-link',
    component: Layout,
    name: 'Menu1',
    meta: {title: '外链', icon: 'el-icon-link'},
    children: [
      {
        // path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
        path: 'https://panjiachen.gitee.io/vue-element-admin-site/zh/',
        meta: {title: 'External Link', icon: 'link'}
      },
      {
        path: 'https://element.eleme.cn/2.13/#/zh-CN/component/installation',
        meta: {title: 'ElmentUI', icon: 'el-icon-postcard'}
      },
      {
        path: 'http://demoadmin.moguit.cn/#/',
        meta: {title: '蘑菇博客后管', icon: 'el-icon-postcard'}
      },

    ]
  },

  // 404 page must be placed at the end !!!
  {path: '*', redirect: '/404', hidden: true}
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({y: 0}),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
