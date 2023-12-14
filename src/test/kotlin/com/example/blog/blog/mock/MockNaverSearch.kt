package com.example.blog.blog.mock

import com.example.blog.blog.domain.BlogType
import com.example.blog.external.adapter.BlogSearchRepository
import com.example.blog.external.dto.response.BlogSearchApiResponse
import com.example.blog.external.naver.dto.Item
import com.example.blog.external.naver.dto.NaverResponse

class MockNaverSearch : BlogSearchRepository {
    override fun searchBlog(
        keyword: String,
        sort: String?,
        page: Int?,
        size: Int?,
        domain: String?
    ): BlogSearchApiResponse {
        return BlogSearchApiResponse(null, naverResponse(size))
    }

    override fun domain(): BlogType {
        return BlogType.NAVER
    }

    fun naverResponse(size: Int?) = NaverResponse(
        start = 1,
        display = size ?: 10,
        total = 360,
        items = listOf(
            Item(
                title = "2023/07/03 주식매매 일지",
                link = "https://blog.naver.com/soft-cream/223145871913",
                description = "2023/07/03 주식매매 일지",
                bloggername = "소프트크림",
                bloggerlink = "https://blog.naver.com/soft-cream",
                postdate = "2023-07-03T00:00:00.000+09:00"
            )
        )
    )
}