package com.example.blog.blog.service

import com.example.blog.blog.domain.Keyword
import com.example.blog.blog.infrastructure.KeywordEntity
import com.example.blog.blog.service.adapter.KeywordRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PopularKeywordService(
    private val keywordRepository: KeywordRepository
) {

    fun findTop10Keywords(): List<Keyword> {
        val findTop10 = keywordRepository.findTop10()

        return findTop10.map {
            KeywordEntity.toModel(it)
        }
    }
}