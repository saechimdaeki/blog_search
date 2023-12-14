package com.example.blog.external.domain

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class SortTypeTest {

    @Test
    @DisplayName("현재 명시된 도메인과 sortType으로 해당하는 도메인의 요청값을 받을 수 있다")
    fun getExistSortType() {
        //given
        val type = "accuracy"
        val domain = "kakao"

        //when
        val sortType = SortType.findSortType(type, domain)

        //then
        assertThat(sortType).isNotNull()
        assertThat(sortType).isEqualTo(SortType.accuracy.name)
    }

    @Test
    @DisplayName("도메인과 type 하나라도 정의되어 있지 않다면 sortType은 null이다")
    fun getNonExistSortType() {
        //given
        val type = "accuracy"
        val domain = "SaechimDaeki"

        //when
        val sortType = SortType.findSortType(type, domain)

        //then
        assertThat(sortType).isNull()
    }
}