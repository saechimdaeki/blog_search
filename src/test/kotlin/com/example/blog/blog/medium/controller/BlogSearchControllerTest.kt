package com.example.blog.blog.medium.controller

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BlogSearchControllerTest @Autowired constructor(
    private val context: WebApplicationContext
) {

    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .addFilters<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .build()
    }

    @Test
    @DisplayName("도메인을 입력하지 않은 통합 검색 서비스는 카카오 api를 호출한 결과를 나타내야 한다")
    fun defaultBlogSearch() {
        //given
        //when
        //then
        val sut = mockMvc.perform(
            get("/search")
                .param("query", "테스트")
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.content").isNotEmpty)
            .andExpect(jsonPath("$.content").isNotEmpty)
            .andExpect(jsonPath("$.totalCount").isNotEmpty)
            .andExpect(jsonPath("$.pageableCount").isNotEmpty)
            .andExpect(jsonPath("$.isEnd").isNotEmpty)
            .andExpect(jsonPath("$.content").isArray)
            .andExpect(jsonPath("$.domain").value("KAKAO"))
    }

    @Test
    @DisplayName("도메인에 네이버를 입력하면 네이버 api를 호출한 결과를 나타내야 한다")
    fun naverBlogSearch() {
        //given
        //when
        //then
        val sut = mockMvc.perform(
            get("/search")
                .param("query", "테스트")
                .param("domain", "NAVER")
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.content").isNotEmpty)
            .andExpect(jsonPath("$.content").isNotEmpty)
            .andExpect(jsonPath("$.totalCount").isNotEmpty)
            .andExpect(jsonPath("$.displayCount").isNotEmpty)
            .andExpect(jsonPath("$.isEnd").isNotEmpty)
            .andExpect(jsonPath("$.content").isArray)
            .andExpect(jsonPath("$.domain").value("NAVER"))
    }

    @Test
    @DisplayName("존재하지 않는 도메인을 입력하면 400 에러와 정의한 메시지를 반환한다")
    fun errorWithNonExistDomainSearch() {
        //given
        //when
        //then
        val sut = mockMvc.perform(
            get("/search")
                .param("query", "테스트")
                .param("domain", "saechimdaeki")
        ).andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").value("지원하지 않는 도메인 입니다 요청하신 도메인은 다음과 같습니다: saechimdaeki"))
    }

    @Test
    @DisplayName("블로그 검색시 사이즈를 30이상으로 설정하면 400 에러와 정의한 메시지를 반환한다")
    fun errorWithValidationParameter_size() {
        //given
        //when
        //then
        val sut = mockMvc.perform(
            get("/search")
                .param("size", "31")
                .param("query", "테스트")
                .param("domain", "naver")
        ).andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").isString)
    }

    @Test
    @DisplayName("블로그 검색시 페이지를 음수로 설정하면 400 에러와 정의한 메시지를 반환한다")
    fun errorWithValidationParameter_page() {
        //given
        //when
        //then
        val sut = mockMvc.perform(
            get("/search")
                .param("page", "-1")
                .param("query", "테스트")
                .param("domain", "naver")
        ).andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").isString)
    }
}