package com.example.blog.blog.infrastructure

import com.example.blog.blog.domain.Keyword
import com.example.blog.common.infrastructure.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "keyword_table")
class KeywordEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    val id: Long? = null,

    val keyword: String,

    var count: Long? = 1L

) : BaseEntity() {

    fun updateCount() {
        count = count?.plus(1)
    }

    companion object {
        fun toModel(entity: KeywordEntity) = Keyword(
            keyword = entity.keyword,
            count = entity.count ?: 1
        )
    }

}