package com.example.blog.common.infrastructure

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {

    @CreatedDate
    @Column(name = "createdAt", nullable = false, updatable = false)
    var createdDate = LocalDate.MIN
        private set

    @LastModifiedDate
    @Column(name = "modifiedAt", nullable = false)
    var updatedAt = LocalDateTime.MIN
        private set
}