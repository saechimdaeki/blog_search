package com.example.blog.blog.infrastructure

import com.example.blog.blog.service.adapter.CacheSearchRepository
import com.example.blog.common.exception.BlogSearchException
import com.example.blog.external.adapter.DomainFactory
import com.example.blog.external.dto.response.Pagination
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Repository


@Repository
class BlogSearchRepositoryImpl(
    private val domainFactory: DomainFactory
) : CacheSearchRepository {

    @Cacheable(value = ["blogSearch"], key = "#keyword + #sortType + #page + #size + #domain")
    @Scheduled(fixedDelay = 60000)
    override fun searchBlog(keyword: String, sortType: String?, page: Int?, size: Int?, domain: String?): Pagination<out Any> {
        domainFactory.getDomain(domain).let {
            val searchBlog = it.searchBlog(keyword, sortType, page, size, domain)
            return searchBlog.kakaoResponse?.kakaoResponseToPagination()
                ?: searchBlog.naverResponse?.naverResponseToPagination() ?: throw BlogSearchException()
        }
    }
}