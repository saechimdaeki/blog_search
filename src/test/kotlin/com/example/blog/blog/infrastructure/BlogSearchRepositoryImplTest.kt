package com.example.blog.blog.infrastructure

import com.example.blog.blog.domain.BlogType
import com.example.blog.blog.mock.MockDomainFactory
import com.example.blog.common.exception.BlogSearchException
import com.example.blog.common.exception.DomainNotValidException
import com.example.blog.external.kakao.dto.Document
import com.example.blog.external.naver.dto.Item
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class BlogSearchRepositoryImplTest {

    private val domainFactory = MockDomainFactory()
    private val blogSearchRepository = BlogSearchRepositoryImpl(domainFactory)

    @Test
    @DisplayName("domain에 값을 입력하지 않으면 카카오 블로그 검색이 우선으로 동작한다")
    fun kakaoBlogSearch_withDomainNull() {

        //given
        //when
        val searchBlog = blogSearchRepository.searchBlog("주식 뉴스", "accuracy", 1, 10, null)

        //then
        assertThat(searchBlog.domain).isEqualTo(BlogType.KAKAO.name)
        assertThat(searchBlog.content).isNotEmpty
        assertThat(searchBlog.content[0]).isInstanceOf(Document::class.java)
    }


    @Test
    @DisplayName("domain에 값을 입력하면 그 도메인이 존재한다면 해당하는 도메인 블로그 검색을 실행한다")
    fun domainBlogSearch_withDomainNotNull() {

        //given
        val domain = "naver"
        //when
        val searchBlog = blogSearchRepository.searchBlog("주식 뉴스", "accuracy", 1, 10, domain)

        //then
        assertThat(searchBlog.domain).isEqualTo(BlogType.NAVER.name)
        assertThat(searchBlog.content).isNotEmpty
        assertThat(searchBlog.content[0]).isInstanceOf(Item::class.java)
    }


    @Test
    @DisplayName("domain이 등록되지 않은 domain이름이라면 예외를 발생시킨다")
    fun domainBlogSearch_withNonRegistered() {

        //given
        val domain = "Github"

        //when
        //then
        assertThatThrownBy {
            blogSearchRepository.searchBlog("주식 뉴스", "accuracy", 1, 10, domain)
        }.isInstanceOf(DomainNotValidException::class.java)

    }

    @Test
    @DisplayName("카카오 검색시 예외가 발생하면 네이버 검색을 실행하고 반환한다")
    fun naverBlogSearch_when_kakaoBlogSearchException() {

        //given
        val keyword = "saechimdaeki"
        val domain = "kakao"

        //when
        val searchBlog = blogSearchRepository.searchBlog(keyword, "accuracy", 1, 10, domain)

        //then
        assertThat(searchBlog.domain).isEqualTo(BlogType.NAVER.name)
        assertThat(searchBlog.content).isNotEmpty
        assertThat(searchBlog.content[0]).isInstanceOf(Item::class.java)
    }


    @Test
    @DisplayName("블로그 검색시 블로그의 데이터가 존재 하지 않으면 예외가 발생한다")
    fun blogSearchException_when_blogSearchNoData() {

        //given
        val keyword = "exception"
        val domain = "kakao"

        //when
        //then
        assertThatThrownBy {
            blogSearchRepository.searchBlog(keyword, "accuracy", 1, 10, domain)
        }.isInstanceOf(BlogSearchException::class.java)
            .hasMessage("해당하는 블로그 글을 찾을 수 없습니다")
    }


}