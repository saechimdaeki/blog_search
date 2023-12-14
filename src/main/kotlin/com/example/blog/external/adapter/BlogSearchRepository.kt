package com.example.blog.external.adapter

import com.example.blog.blog.domain.BlogType
import com.example.blog.external.dto.response.BlogSearchApiResponse

interface BlogSearchRepository {
    fun searchBlog(keyword: String, sort: String?, page: Int?, size: Int?, domain: String?): BlogSearchApiResponse

    fun domain(): BlogType
}