package com.example.blog.common.config

import feign.Client
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfiguration {

    @Bean
    fun feignClient() : Client {
        return Client.Default(null, null)
    }
}