package com.example.blog.blog.mock

import com.example.blog.blog.infrastructure.KeywordEntity
import com.example.blog.blog.service.adapter.KeywordRepository

class MockKeywordRepository : KeywordRepository {

    private var id = 1L

    private val mockDataBase = mutableMapOf<Long, KeywordEntity>()

    override fun findTop10(): List<KeywordEntity> {
        return mockDataBase.values.sortedByDescending { it.count }.take(10)
    }

    override fun findKeyword(keyword: String): KeywordEntity? {
        return mockDataBase.values.firstOrNull() {
            it.keyword == keyword
        }?.let {
            it.updateCount()
            it
        }
    }

    override fun save(keyword: String): KeywordEntity {
        val keywordEntity = KeywordEntity(keyword = keyword)
        mockDataBase[id++] = keywordEntity
        return keywordEntity
    }
}