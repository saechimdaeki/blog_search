package com.example.blog.external.kakao.infrastructure

import com.example.blog.blog.domain.BlogType
import com.example.blog.common.exception.BlogSearchException
import com.example.blog.external.adapter.BlogSearchRepository
import com.example.blog.external.domain.SortType
import com.example.blog.external.dto.response.BlogSearchApiResponse
import com.example.blog.external.kakao.api.KakaoBlogSearch
import com.example.blog.external.naver.api.NaverBlogSearch
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class KaKaoBlogSearchRepositoryImpl(
    private val kakaoBlogSearch: KakaoBlogSearch,
    private val naverBlogSearch: NaverBlogSearch,
) : BlogSearchRepository {


    @Value("\${kakao.api-key}")
    lateinit var kakaoAuth: String

    @Value("\${naver.client-id}")
    lateinit var clientId: String

    @Value("\${naver.client-secret}")
    lateinit var clientSecret: String
    override fun searchBlog(keyword: String, sort: String?, page: Int?, size: Int?, domain: String?): BlogSearchApiResponse {

        val kakaoFuture = kakaoSearch(keyword, sort, page, size)

        val naverFuture = naverSearch(keyword, sort, page, size, domain)

        CompletableFuture.allOf(kakaoFuture, naverFuture).join()

        val kakaoResponse = kakaoFuture.get()
        val naverResponse = naverFuture.get()

        if (kakaoResponse == null && naverResponse == null)
            throw BlogSearchException()

        return BlogSearchApiResponse(
            kakaoResponse = kakaoResponse,
            naverResponse = naverResponse
        )
    }


    override fun domain(): BlogType {
        return BlogType.KAKAO
    }

    private fun kakaoSearch(keyword: String, sort: String?, page: Int?, size: Int?) = CompletableFuture.supplyAsync {
        kakaoBlogSearch.blogSearch(
            authorization = kakaoAuth,
            query = keyword,
            sort = sort,
            page = page,
            size = size
        )
    }.exceptionally { null }

    private fun naverSearch(keyword: String, sort: String?, page: Int?, size: Int?, domain: String?) = CompletableFuture.supplyAsync {
        naverBlogSearch.blogSearch(
            clientId = clientId,
            clientSecret = clientSecret,
            query = keyword,
            sort = SortType.findSortType(sort, domain),
            display = size,
            start = page
        )
    }.exceptionally { null }


}
