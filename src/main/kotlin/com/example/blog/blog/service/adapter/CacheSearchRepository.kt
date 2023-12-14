package com.example.blog.blog.service.adapter

import com.example.blog.external.dto.response.Pagination

interface CacheSearchRepository {

    fun searchBlog(keyword: String, sortType: String?, page: Int?, size: Int?, domain: String?): Pagination<out Any>
}