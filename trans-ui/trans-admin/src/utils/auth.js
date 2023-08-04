import Cookies from 'js-cookie'

const TokenKey = 'tokenHeader'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  //删除登陆时添加在cookies中的用户role
  return Cookies.remove(TokenKey)
}
