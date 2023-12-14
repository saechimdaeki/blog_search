package com.example.blog.external.adapter

interface DomainFactory {
    fun getDomain(blogType: String?): BlogSearchRepository
}