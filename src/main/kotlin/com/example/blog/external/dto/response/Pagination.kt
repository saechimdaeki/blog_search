package com.example.blog.external.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
class Pagination<T>(
    val content: List<T>,
    val totalCount: Int,
    val pageableCount: Int?,
    val displayCount: Int,
    val isEnd: Boolean,
    val domain: String
)