package com.example.blog.blog.infrastructure

import com.example.blog.blog.service.adapter.KeywordRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class KeywordRepositoryImpl(
    private val keywordJpaRepository: KeywordJpaRepository
) : KeywordRepository {
    override fun findTop10(): List<KeywordEntity> {
        return keywordJpaRepository.findTopKeywords(Pageable.ofSize(10))
    }

    override fun findKeyword(keyword: String): KeywordEntity? {
        return keywordJpaRepository.findByKeyword(keyword)
    }

    override fun save(keyword: String): KeywordEntity {
        return keywordJpaRepository.save(KeywordEntity(keyword = keyword))
    }
}