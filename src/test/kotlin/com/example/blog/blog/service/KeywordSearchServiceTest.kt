package com.example.blog.blog.service

import com.example.blog.blog.domain.BlogType
import com.example.blog.blog.mock.MockCacheSearchRepository
import com.example.blog.blog.mock.MockKeywordRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


class KeywordSearchServiceTest {

    val keywordRepository = MockKeywordRepository()
    val cacheSearchRepository = MockCacheSearchRepository()

    @Test
    @DisplayName("키워드가 이미 db에 존재한다면 카운트를 늘리고 없다면 저장한다.")
    fun getBlogSearchInfo() {
        //given
        val sut = KeywordSearchService(keywordRepository, cacheSearchRepository)

        //when
        sut.getBlogSearchInfo("카카오", "accuracy", 1, 10, "kakao")
        val findKeyword = keywordRepository.findKeyword("카카오")

        //then

        assertThat(findKeyword).isNotNull
        assertThat(findKeyword?.keyword).isEqualTo("카카오")
        assertThat(findKeyword?.count).isEqualTo(2)
    }

    @Test
    @DisplayName("키워드를 통해 블로그 검색을 실행한다. domain을 입력하지 않는다면 카카오 검색을 실행한다")
    fun defaultBlogSearchInfo() {
        //given
        val sut = KeywordSearchService(keywordRepository, cacheSearchRepository)

        //when
        val blogSearchInfo = sut.getBlogSearchInfo("카카오", "accuracy", 1, 10, null)

        //then

        assertThat(blogSearchInfo).isNotNull
        assertThat(blogSearchInfo.domain).isEqualTo(BlogType.KAKAO.name)
        assertThat(blogSearchInfo.content).isNotNull
    }


    @Test
    @DisplayName("키워드를 통해 블로그 검색을 실행한다. domain을 입력하면 해당 도메인에서 블로그를 검색한다")
    fun domainBlogSearchInfo() {
        //given
        val sut = KeywordSearchService(keywordRepository, cacheSearchRepository)
        val domain = "naver"
        //when
        val blogSearchInfo = sut.getBlogSearchInfo("카카오", "accuracy", 1, 10, domain)

        //then

        assertThat(blogSearchInfo).isNotNull
        assertThat(blogSearchInfo.domain).isEqualTo(BlogType.NAVER.name)
        assertThat(blogSearchInfo.content).isNotNull
    }
}