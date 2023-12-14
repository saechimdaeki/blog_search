package com.example.blog.blog.medium.infrastructure

import com.example.blog.blog.domain.BlogType
import com.example.blog.blog.service.adapter.CacheSearchRepository
import com.example.blog.common.exception.DomainNotValidException
import com.example.blog.external.kakao.dto.Document
import com.example.blog.external.naver.dto.Item
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BlogSearchRepositoryImplTest @Autowired constructor(
    private val blogSearchRepository: CacheSearchRepository
) {
    @Test
    @DisplayName("domain에 값을 입력함에 따라 해당하는 도메인의 데이터를 조회한다.")
    fun getblogDomainSearch() {

        // given
        val domain = "naver"
        val keyword = "주식 뉴스"

        // when
        val sut = blogSearchRepository.searchBlog(keyword, "accuracy", 1, 10, domain)

        // then
        assertThat(sut.domain).isEqualTo(domain.uppercase())
        assertThat(sut.content).isNotEmpty
        assertThat(sut.content[0]).isInstanceOf(Item::class.java)
    }


    @Test
    @DisplayName("domain에 값을 입력하지 않으면 자동으로 카카오 블로그를 검색한다")
    fun defaultblogDomainSearch() {

        // given
        val keyword = "주식 뉴스"

        // when
        val sut = blogSearchRepository.searchBlog(keyword, "accuracy", 1, 10, null)

        // then
        assertThat(sut.domain).isEqualTo(BlogType.KAKAO.name)
        assertThat(sut.content).isNotEmpty
        assertThat(sut.content[0]).isInstanceOf(Document::class.java)
    }

    @Test
    @DisplayName("존재하지 않는 도메인을 입력하면 예외가 발생한다")
    fun errorWithNoneExistDomainSearch() {

        // given
        val keyword = "주식 뉴스"
        val domain = "saechimdaeki"

        // when
        //then
        assertThatThrownBy {
            blogSearchRepository.searchBlog(keyword, "accuracy", 1, 10, domain)
        }.isInstanceOf(DomainNotValidException::class.java)
            .hasMessage("지원하지 않는 도메인 입니다 요청하신 도메인은 다음과 같습니다: $domain")
    }

}