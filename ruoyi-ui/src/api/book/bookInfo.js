import request from '@/utils/request'

// 查询图书信息列表
export function listBookInfo(query) {
    return request({
      url: '/book/bookInfo/list',
      method: 'get',
      params: query
    })
  }

  // 查询图书信息详情
export function detail(id) {
  return request({
    url: '/book/bookInfo/detail/'+ id,
    method: 'get',
    
  })
}

// 修改图书信息
export function updateBookInfo(data) {
  return request({
    url: '/book/bookInfo',
    method: 'put',
    data:data,
    
  })
}

// 修改图书信息
export function addBookInfo(data) {
  return request({
    url: '/book/bookInfo/add',
    method: 'post',
    data:data,
    
  })
}