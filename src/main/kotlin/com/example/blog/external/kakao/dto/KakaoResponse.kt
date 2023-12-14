package com.example.blog.external.kakao.dto

import com.example.blog.blog.domain.BlogType
import com.example.blog.external.dto.response.Pagination
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
class KakaoResponse(
    val meta: Meta,
    val documents: List<Document>
) {
    fun kakaoResponseToPagination(): Pagination<Document> {
        return Pagination(
            content = this.documents,
            totalCount = this.meta.totalCount,
            pageableCount = this.meta.pageableCount,
            isEnd = this.meta.isEnd,
            domain = BlogType.KAKAO.name,
            displayCount = this.documents.size
        )
    }
}

@JsonNaming(SnakeCaseStrategy::class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
class Meta(
    val totalCount: Int,
    val pageableCount: Int,
    val isEnd: Boolean
) {

}

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
class Document(
    val title: String,
    val contents: String,
    val url: String,
    val blogname: String,
    val thumbnail: String,
    val datetime: String
)