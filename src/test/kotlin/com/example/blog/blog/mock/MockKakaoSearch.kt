package com.example.blog.blog.mock

import com.example.blog.blog.domain.BlogType
import com.example.blog.external.adapter.BlogSearchRepository
import com.example.blog.external.dto.response.BlogSearchApiResponse
import com.example.blog.external.kakao.dto.Document
import com.example.blog.external.kakao.dto.KakaoResponse
import com.example.blog.external.kakao.dto.Meta
import com.example.blog.external.naver.dto.Item
import com.example.blog.external.naver.dto.NaverResponse

class MockKakaoSearch : BlogSearchRepository {

    override fun searchBlog(
        keyword: String,
        sort: String?,
        page: Int?,
        size: Int?,
        domain: String?
    ): BlogSearchApiResponse {
        return if ("saechimdaeki" == keyword) {
            BlogSearchApiResponse(null, naverResponse(size))
        } else if ("exception" == keyword) {
            BlogSearchApiResponse(null, null)
        } else
            BlogSearchApiResponse(kakaoResponse(), naverResponse(size))
    }

    override fun domain(): BlogType {
        return BlogType.KAKAO
    }


    fun kakaoResponse() = KakaoResponse(
        documents = listOf(
            Document(
                title = "2023/07/03 주식매매 일지",
                contents = "2023/07/03 주식매매 일지",
                url = "https://blog.naver.com/soft-cream/223145871913",
                blogname = "소프트크림",
                thumbnail = "https://search3.kakaocdn.net/argon/130x130_85_c/7Q8Z1KQYj8",
                datetime = "2023-07-03T00:00:00.000+09:00"
            )
        ),
        meta = Meta(
            totalCount = 3600,
            pageableCount = 10,
            isEnd = false
        )
    )

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