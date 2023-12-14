package com.example.blog.blog.medium.controller

import com.example.blog.blog.infrastructure.KeywordEntity
import com.example.blog.blog.infrastructure.KeywordJpaRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class PopularSearchControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val keywordJpaRepository: KeywordJpaRepository
) {
    @Test
    @DisplayName("인기 검색어를 조회한다, 아무런 조회 데이터가 없다면 빈 리스트를 반환한다")
    fun getPopularKeywordsWithEmpty() {
        //given
        //when
        //then
        val sut = mockMvc.perform(
            get("/popular")
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$").isEmpty)
    }

    @Test
    @DisplayName("인기 검색어 데이터가 저장된 후 , 조회 데이터가 있다면 해당하는 리스트를 반환한다")
    fun getPopularKeywordsWithKeywords() {
        //given

        keywordJpaRepository.save(KeywordEntity(keyword = "카카오", count = 15))
        keywordJpaRepository.save(KeywordEntity(keyword = "카카오뱅크", count = 20))
        keywordJpaRepository.save(KeywordEntity(keyword = "카카오페이", count = 30))

        //when
        //then
        val sut = mockMvc.perform(
            get("/popular")
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$[0].keyword").value("카카오페이"))
            .andExpect(jsonPath("$[0].count").value(30))
            .andExpect(jsonPath("$[1].keyword").value("카카오뱅크"))
            .andExpect(jsonPath("$[1].count").value(20))
            .andExpect(jsonPath("$[2].keyword").value("카카오"))
            .andExpect(jsonPath("$[2].count").value(15))
    }

    @AfterEach
    fun delteAll() {
        keywordJpaRepository.deleteAll()
    }

}