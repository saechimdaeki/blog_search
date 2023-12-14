package com.example.blog.common.exception

class DomainNotValidException(override val message: String? = "지원하지 않는 도메인입니다" )
    : RuntimeException()