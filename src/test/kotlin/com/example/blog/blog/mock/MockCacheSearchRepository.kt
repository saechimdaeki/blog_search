package com.example.blog.blog.mock

import com.example.blog.blog.domain.BlogType
import com.example.blog.blog.service.adapter.CacheSearchRepository
import com.example.blog.external.dto.response.Pagination
import com.example.blog.external.kakao.dto.Document

class MockCacheSearchRepository : CacheSearchRepository {
    override fun searchBlog(
        keyword: String,
        sortType: String?,
        page: Int?,
        size: Int?,
        domain: String?
    ): Pagination<out Any> {
        return Pagination(
            content = listOf(
                Document(
                    title = "2023/07/03 주식매매 일지",
                    contents = "2023/07/03 주식매매 일지",
                    url = "https://blog.naver.com/soft-cream/223145871913",
                    blogname = "소프트크림",
                    thumbnail = "https://search3.kakaocdn.net/argon/130x130_85_c/7Q8Z1KQYj8",
                    datetime = "2023-07-03T00:00:00.000+09:00"
                )
            ),
            totalCount = 3600,
            pageableCount = 10,
            isEnd = false,
            domain = domain?.uppercase() ?: BlogType.KAKAO.name,
            displayCount = size ?: 0
        )
    }
}