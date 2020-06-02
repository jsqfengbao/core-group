import request from '@/request'

export function getAllTags() {
  return request({
    url: '/blog/api/tags',
    method: 'get',
  })
}

export function getAllTagsDetail() {
  return request({
    url: '/blog/api/tags',
    method: 'get',
  })
}

export function getHotTags() {
  return request({
    url: '/blog/api/tags/hot',
    method: 'get',
  })
}

export function getTag(id) {
  return request({
    url: `/blog/api/tags/${id}`,
    method: 'get',
  })
}

export function getTagDetail(id) {
  return request({
    url: `/blog/api/tags/detail/${id}`,
    method: 'get',
  })
}
