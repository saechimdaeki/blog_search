package com.example.blog.external.dto.response

import com.example.blog.external.kakao.dto.KakaoResponse
import com.example.blog.external.naver.dto.NaverResponse


data class BlogSearchApiResponse(
    val kakaoResponse: KakaoResponse?,
    val naverResponse: NaverResponse?
) {
}