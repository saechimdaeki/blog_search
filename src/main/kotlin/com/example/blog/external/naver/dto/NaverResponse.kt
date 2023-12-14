package com.example.blog.external.naver.dto

import com.example.blog.blog.domain.BlogType
import com.example.blog.external.dto.response.Pagination
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude


@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
class NaverResponse(
    val start: Int,
    val display: Int,
    val total: Int,
    val items: List<Item>
) {
    fun naverResponseToPagination(): Pagination<Item> {
        return Pagination(
            content = this.items,
            totalCount = this.total,
            displayCount = this.display,
            isEnd = this.start + this.display >= this.total,
            domain = BlogType.NAVER.name,
            pageableCount = null
        )
    }
}

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
class Item(
    val title: String,
    val link: String,
    val description: String,
    val bloggername: String,
    val bloggerlink: String,
    val postdate: String
)

