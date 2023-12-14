package com.example.blog.external.naver.infrastructure

import com.example.blog.blog.domain.BlogType
import com.example.blog.common.exception.BlogSearchException
import com.example.blog.external.adapter.BlogSearchRepository
import com.example.blog.external.domain.SortType
import com.example.blog.external.dto.response.BlogSearchApiResponse
import com.example.blog.external.naver.api.NaverBlogSearch
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class NaverBlogSearchRepositoryImpl(
    private val naverBlogSearch: NaverBlogSearch
) : BlogSearchRepository {

    @Value("\${naver.client-id}")
    lateinit var clientId: String

    @Value("\${naver.client-secret}")
    lateinit var clientSecret: String

    override fun searchBlog(
        keyword: String,
        sort: String?,
        page: Int?,
        size: Int?,
        domain: String?
    ): BlogSearchApiResponse {
        val blogSearch = naverBlogSearch.blogSearch(
            clientId = clientId,
            clientSecret = clientSecret,
            query = keyword,
            display = size,
            start = page,
            sort = SortType.findSortType(sort, domain)
        ) ?: throw BlogSearchException()

        return BlogSearchApiResponse(
            kakaoResponse = null,
            naverResponse = blogSearch
        )
    }

    override fun domain(): BlogType {
        return BlogType.NAVER
    }
}