import request from '@/request'

export function getAllCategorys() {
  return request({
    url: '/blog/api/categorys',
    method: 'get',
  })
}

export function getAllCategorysDetail() {
  return request({
    url: '/blog/api/categorys',
    method: 'get',
  })
}

export function getCategory(id) {
  return request({
    url: `/blog/api/category/${id}`,
    method: 'get',
  })
}

export function getCategoryDetail(id) {
  return request({
    url: `/blog/api/category/${id}`,
    method: 'get',
  })
}
