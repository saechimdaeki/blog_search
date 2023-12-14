package com.example.blog.blog.medium.service

import com.example.blog.blog.domain.BlogType
import com.example.blog.blog.infrastructure.KeywordEntity
import com.example.blog.blog.infrastructure.KeywordJpaRepository
import com.example.blog.blog.service.KeywordSearchService
import com.example.blog.common.exception.DomainNotValidException
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class KeywordSearchServiceTest @Autowired constructor(
    private val keywordSearchService: KeywordSearchService,
    private val keywordJpaRepository: KeywordJpaRepository
) {
    @Test
    @DisplayName("키워드를 조회하고 해당하는 키워드의 데이터가 있다면 조회수를 1회 늘린다")
    fun searchExistKeywordCount() {
        //given
        val keyword = "카카오"

        keywordSearchService.getBlogSearchInfo(keyword, "accuracy", 1, 10, "kakao")
        // when
        keywordJpaRepository.findByKeyword(keyword)?.let {
            //then
            assertThat(it).isNotNull
            assertThat(it.keyword).isEqualTo(keyword)
            assertThat(it.count).isEqualTo(2)
        }
    }

    @Test
    @DisplayName("새로 조회하는 키워드라면 그 키워드의 조회수는 1로 저장된다")
    fun searchNewKeywordCount() {
        //given
        val keyword = "새침한카카오"
        keywordSearchService.getBlogSearchInfo(keyword, "accuracy", 1, 10, "kakao")

        // when
        keywordJpaRepository.findByKeyword(keyword)?.let {
            //then
            assertThat(it).isNotNull
            assertThat(it.keyword).isEqualTo(keyword)
            assertThat(it.count).isEqualTo(1)
        }
    }

    @Test
    @DisplayName("도메인에 입력한 데이터를 기준으로 키워드를 조회한다.")
    fun naverDomainSearchBlogData() {
        //given
        val keyword = "새침한 네이버"
        //when
        val blogSearchInfo = keywordSearchService.getBlogSearchInfo(keyword, "accuracy", 1, 10, "naver")

        //then
        assertThat(blogSearchInfo).isNotNull
        assertThat(blogSearchInfo.domain).isEqualTo(BlogType.NAVER.name)
        assertThat(blogSearchInfo.totalCount).isPositive()
        assertThat(blogSearchInfo.content).isNotEmpty
        assertThat(blogSearchInfo.content).isInstanceOf(List::class.java)
        assertThat(blogSearchInfo.pageableCount).isNull()
        assertThat(blogSearchInfo.isEnd).isNotNull()
    }


    @Test
    @DisplayName("도메인에 도메인명을 입력하지 않으면 카카오 블로그를 검색한다.")
    fun defaultSearchBlogData() {
        //given
        val keyword = "새침한 카카오"
        //when
        val blogSearchInfo = keywordSearchService.getBlogSearchInfo(keyword, "accuracy", 1, 10, null)

        //then
        assertThat(blogSearchInfo).isNotNull
        assertThat(blogSearchInfo.domain).isEqualTo(BlogType.KAKAO.name)
        assertThat(blogSearchInfo.totalCount).isPositive()
        assertThat(blogSearchInfo.content).isNotEmpty
        assertThat(blogSearchInfo.content).isInstanceOf(List::class.java)
        assertThat(blogSearchInfo.pageableCount).isPositive()
        assertThat(blogSearchInfo.isEnd).isNotNull()
    }

    @Test
    @DisplayName("존재하지 않는 도메인을 입력할 시 에러를 발생시켜야한다.")
    fun errorWithNoExistDomain() {
        //given
        val keyword = "새침한 카카오"
        //when
        //then
        assertThatThrownBy {
            keywordSearchService.getBlogSearchInfo(keyword, "accuracy", 1, 10, "새침대기")
        }.isInstanceOf(DomainNotValidException::class.java)

    }


    @BeforeEach
    fun initData() {
        keywordJpaRepository.save(KeywordEntity(keyword = "카카오"))
        keywordJpaRepository.save(KeywordEntity(keyword = "네이버"))
    }

    @AfterEach
    fun deleteAll() {
        keywordJpaRepository.deleteAll()
    }
}