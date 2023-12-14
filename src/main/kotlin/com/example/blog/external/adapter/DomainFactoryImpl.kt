package com.example.blog.external.adapter

import com.example.blog.common.exception.DomainNotValidException
import org.springframework.stereotype.Component

@Component
class DomainFactoryImpl(blogSearchRepositories: List<BlogSearchRepository>): DomainFactory {
    private val domainMap: Map<String, BlogSearchRepository> = blogSearchRepositories.associateBy { it.domain().name.lowercase() }

    override fun getDomain(blogType: String?): BlogSearchRepository {
        val blogDomain = blogType?.lowercase() ?: "kakao"

        return domainMap[blogDomain] ?: throw DomainNotValidException("지원하지 않는 도메인 입니다 요청하신 도메인은 다음과 같습니다: $blogDomain")
    }
}