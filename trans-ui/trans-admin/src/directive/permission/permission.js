import store from '@/store'

function checkPermission(el, binding) {
  const { value } = binding
  const roles = store.getters && store.getters.roles
  const buttonMap = store.getters.buttonMap

  if (value && value.length > 0) {

      /*const permissionRoles = value

      const hasPermission = roles.some(role => {
        return permissionRoles.includes(role)
      })

      if (!hasPermission) {
        el.parentNode && el.parentNode.removeChild(el)
      }*/
      const path = value
      const hasPermission = buttonMap.get(path)
      if (!hasPermission) {
        el.parentNode && el.parentNode.removeChild(el)
      }

  } else {
    throw new Error(`need roles! Like v-permission="['admin','editor']"`)
  }
}

export default {
  inserted(el, binding) {
    checkPermission(el, binding)
  },
  update(el, binding) {
    checkPermission(el, binding)
  }
}
