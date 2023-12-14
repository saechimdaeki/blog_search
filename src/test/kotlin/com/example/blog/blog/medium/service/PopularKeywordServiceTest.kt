package com.example.blog.blog.medium.service

import com.example.blog.blog.infrastructure.KeywordEntity
import com.example.blog.blog.infrastructure.KeywordJpaRepository
import com.example.blog.blog.service.PopularKeywordService
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class PopularKeywordServiceTest @Autowired constructor(
    private val popularKeywordService: PopularKeywordService,
    private val keywordJpaRepository: KeywordJpaRepository
) {
    @Test
    @DisplayName("인기 키워드를 높은 조회 순으로 조회한다")
    fun getPopularKeywords() {
        //given
        //when
        val popularKeywords = popularKeywordService.findTop10Keywords()
        val onlyKeywords = popularKeywords.map { it.keyword }

        //then
        assertThat(popularKeywords.size).isEqualTo(3)
        assertThat(onlyKeywords).containsExactly("카카오페이", "카카오뱅크", "카카오")
        assertThat(popularKeywords[0].keyword).isNotEmpty()
        assertThat(popularKeywords[0].keyword).isEqualTo("카카오페이")
    }


    @BeforeEach
    fun setUp() {
        keywordJpaRepository.save(KeywordEntity( keyword = "카카오", count = 15))
        keywordJpaRepository.save(KeywordEntity( keyword = "카카오뱅크", count = 20))
        keywordJpaRepository.save(KeywordEntity( keyword = "카카오페이", count = 30))
    }

    @AfterEach
    fun deleteAll() {
        keywordJpaRepository.deleteAll()
    }
}