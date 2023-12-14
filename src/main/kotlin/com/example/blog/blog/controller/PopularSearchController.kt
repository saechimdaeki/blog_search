package com.example.blog.blog.controller

import com.example.blog.blog.service.PopularKeywordService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PopularSearchController(
    private val popularKeywordService: PopularKeywordService
) {

    @GetMapping("/popular")
    fun getPopularKeywords(): ResponseEntity<*> {
        return ResponseEntity.ok(popularKeywordService.findTop10Keywords())
    }
}