package com.example.blog.external.domain

enum class SortType(val type: String, val description: String) {
    accuracy("accuracy_kakao", "카카오 검색 정확도순"),
    recency("recency_kakao", "카카오 검색 최신순"),
    sim("accuracy_naver", "네이버 검색 정확도순"),
    date("recency_naver", "네이버 검색 최신순");


    companion object {
        private val typeToNameMap: Map<String, String> by lazy {
            values().associateBy({ it.type }, { it.name })
        }

        fun findSortType(type: String?, domain: String?): String? {
            return typeToNameMap[type + "_" + domain?.lowercase()]
        }
    }

}