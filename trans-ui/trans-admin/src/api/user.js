import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/online/auth/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/online/auth/info',
    method: 'post',
    data: token
  })
}

export function getButtonMap(token) {
  return request({
    url: '/online/menu/buttonList',
    method: 'post',
    data: token
  })
}

export function logout(token) {
  return request({
    url: '/online/auth/logout',
    method: 'post',
    data: token
  })
}
