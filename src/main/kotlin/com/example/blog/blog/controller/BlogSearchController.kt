package com.example.blog.blog.controller

import com.example.blog.blog.service.KeywordSearchService
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
class BlogSearchController(
    private val keywordSearchService: KeywordSearchService,
) {

    @GetMapping("/search")
    fun searchBlogPosting(
        @RequestParam query: String,
        @RequestParam(required = false, defaultValue = "accuracy") sort: String?,
        @RequestParam(required = false, defaultValue = "1")
        @Min(1, message = "최소 1의 페이지 크기를 요청해야 합니다") @Max(40, message = "페이지는 최대 40까지 가능합니다") page: Int?,
        @RequestParam(required = false, defaultValue = "10")
        @Min(1, message = "최소 1건의 사이즈를 요청해야 합니다") @Max(30, message = "최대 30건의 사이즈까지 요청이 가능합니다") size: Int?,
        @RequestParam(required = false, defaultValue = "kakao") domain: String?,
    ): ResponseEntity<*> {
        return ResponseEntity.ok(keywordSearchService.getBlogSearchInfo(query, sort, page, size, domain))
    }

}