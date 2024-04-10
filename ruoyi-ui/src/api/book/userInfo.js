import request from '@/utils/request'

// 查询用户信息管理列表
export function listInfo(query) {
  return request({
    url: '/book/userInfo/list',
    method: 'get',
    params: query
  })
}

// 查询用户信息管理详细
export function getInfo(id) {
  return request({
    url: '/book/userInfo/' + id,
    method: 'get'
  })
}

// 新增用户信息管理
export function addInfo(data) {
  return request({
    url: '/book/userInfo',
    method: 'post',
    data: data
  })
}

// 修改用户信息管理
export function updateInfo(data) {
  return request({
    url: '/book/userInfo',
    method: 'put',
    data: data
  })
}
// 重置密码
export function resetPwd(data) {
  return request({
    url: '/book/userInfo/resetPwd',
    method: 'put',
    data: data
  })
}
// 删除用户信息管理
export function delInfo(id) {
  return request({
    url: '/book/userInfo/' + id,
    method: 'delete'
  })
}
