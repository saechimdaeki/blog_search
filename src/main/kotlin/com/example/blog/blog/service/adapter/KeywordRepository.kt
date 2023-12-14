package com.example.blog.blog.service.adapter

import com.example.blog.blog.infrastructure.KeywordEntity

interface KeywordRepository {
    fun findTop10(): List<KeywordEntity>

    fun findKeyword(keyword: String): KeywordEntity?

    fun save(keyword: String): KeywordEntity
}