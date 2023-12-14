package com.example.blog.external.adapter

import com.example.blog.blog.mock.MockDomainFactory
import com.example.blog.common.exception.DomainNotValidException
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class DomainFactoryImplTest {

    private val domainFactory = MockDomainFactory()

    @Test
    @DisplayName("domainFactory를 통해 등록되어 있는 도메인을 가져온다")
    fun domainSearchTest() {
        //given
        val blogType = "kakao"

        //when
        val blogDomain = domainFactory.getDomain(blogType)

        //then
        assertThat(blogDomain.domain().name).isEqualToIgnoringCase(blogType)
    }

    @Test
    @DisplayName("domainFactory에 등록되어 있지 않은 블로그타입이라면 예외가 발생한다")
    fun domainSearchWithException() {
        //given
        val blogType = "saechimdaeki"

        //when
        //then
        assertThatThrownBy {
            domainFactory.getDomain(blogType)
        }.isInstanceOf(DomainNotValidException::class.java)

    }
}