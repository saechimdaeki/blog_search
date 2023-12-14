package com.example.blog.common.exception

class BlogSearchException(override val message: String? = "해당하는 블로그 글을 찾을 수 없습니다" )
    : RuntimeException()