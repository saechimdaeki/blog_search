package com.example.blog.blog.service

import com.example.blog.blog.service.adapter.CacheSearchRepository
import com.example.blog.blog.service.adapter.KeywordRepository
import com.example.blog.external.dto.response.Pagination
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class KeywordSearchService(
    private val keywordRepository: KeywordRepository,
    private val cacheSearchRepository: CacheSearchRepository
) {

    @Transactional
    fun getBlogSearchInfo(
        keyword: String,
        sortType: String?,
        page: Int?,
        size: Int?,
        domain: String?
    ): Pagination<out Any> {

        keywordRepository.findKeyword(keyword)?.let {
            it.updateCount()
        } ?: keywordRepository.save(keyword)

        cacheSearchRepository.searchBlog(keyword, sortType, page, size, domain).let {
            return it
        }
    }


}