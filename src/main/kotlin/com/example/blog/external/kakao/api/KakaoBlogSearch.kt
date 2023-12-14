package com.example.blog.external.kakao.api

import com.example.blog.external.kakao.dto.KakaoResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "kakaoSearch", url = "\${kakao.url}")
interface KakaoBlogSearch {

    @GetMapping
    fun blogSearch(
        @RequestHeader("Authorization") authorization: String,
        @RequestParam query: String,
        @RequestParam(required = false) sort : String?,
        @RequestParam(required = false) page : Int?,
        @RequestParam(required = false) size : Int?,
    ): KakaoResponse?
}