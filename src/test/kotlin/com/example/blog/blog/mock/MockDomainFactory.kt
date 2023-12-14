package com.example.blog.blog.mock

import com.example.blog.common.exception.DomainNotValidException
import com.example.blog.external.adapter.BlogSearchRepository
import com.example.blog.external.adapter.DomainFactory

class MockDomainFactory : DomainFactory {

    private val map = mapOf(
        "kakao" to MockKakaoSearch(), "naver" to MockNaverSearch()
    )

    override fun getDomain(blogType: String?): BlogSearchRepository {
        val blogDomain = blogType?.lowercase() ?: "kakao"
        return map[blogDomain] ?: throw DomainNotValidException("지원하지 않는 도메인 입니다 요청하신 도메인은 다음과 같습니다: $blogType")
    }
}