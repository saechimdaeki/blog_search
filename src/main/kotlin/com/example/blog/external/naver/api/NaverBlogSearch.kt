package com.example.blog.external.naver.api

import com.example.blog.external.naver.dto.NaverResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "naverSearch", url = "\${naver.url}")
interface NaverBlogSearch {

    @GetMapping
    fun blogSearch(
        @RequestHeader("X-Naver-Client-Id") clientId: String,
        @RequestHeader("X-Naver-Client-Secret") clientSecret: String,
        @RequestParam query: String,
        @RequestParam(required = false) display: Int?,
        @RequestParam(required = false) start: Int?,
        @RequestParam(required = false) sort: String?,
    ) : NaverResponse?
}