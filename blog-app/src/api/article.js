import request from '@/request'


export function getArticles(query, page) {
  return request({
    url: '/blog/api/articles',
    method: 'get',
    params: {
      pageNumber: page.pageNumber,
      pageSize: page.pageSize,
      name: page.name,
      sort: page.sort,
      year: query.year,
      month: query.month,
      tagId: query.tagId,
      categoryId: query.categoryId
    }
  })
}

export function getHotArtices() {
  return request({
    url: '/blog/api/articles/hot',
    method: 'get'
  })
}

export function getNewArtices() {
  return request({
    url: '/blog/api/articles/new',
    method: 'get'
  })
}

export function viewArticle(id) {
  return request({
    url: `/blog/api/article/view/${id}`,
    method: 'get'
  })
}

export function getArticlesByCategory(id) {
  return request({
    url: `/blog/api/articles/category/${id}`,
    method: 'get'
  })
}

export function getArticlesByTag(id) {
  return request({
    url: `/blog/api/articles/tag/${id}`,
    method: 'get'
  })
}


export function publishArticle(article) {
  return request({
    url: '/blog/blogArticle/articles/publish',
    method: 'post',
    data: article
  })
}

export function listArchives() {
  return request({
    url: '/blog/api/archives',
    method: 'get'
  })
}

export function getArticleById(id) {
  return request({
    url: `/blog/api/articles/${id}`,
    method: 'get'
  })
}
