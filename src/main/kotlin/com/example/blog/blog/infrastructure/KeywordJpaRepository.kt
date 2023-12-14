package com.example.blog.blog.infrastructure

import jakarta.persistence.LockModeType
import jakarta.persistence.QueryHint
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.QueryHints

interface KeywordJpaRepository : JpaRepository<KeywordEntity, Long> {


    @Query(value = "SELECT k FROM KeywordEntity k ORDER BY k.count DESC, k.updatedAt DESC")
    fun findTopKeywords(pageable: Pageable): List<KeywordEntity>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(QueryHint(name = "jakarta.persistence.lock.timeout", value = "2000"))
    fun findByKeyword(keyword: String): KeywordEntity?
}