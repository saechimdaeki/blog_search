package com.example.blog.common.advice

import com.example.blog.common.dto.ErrorResponse
import com.example.blog.common.exception.BlogSearchException
import com.example.blog.common.exception.DomainNotValidException
import feign.FeignException
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        BlogSearchException::class,
        DomainNotValidException::class,
        FeignException::class,
        ConstraintViolationException::class
    )
    fun handleException(e: Exception): ErrorResponse {
        return ErrorResponse(HttpStatus.BAD_REQUEST.name, e.message ?: "잘못된 요청입니다")
    }

}
