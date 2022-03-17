package com.example.pudge.configuration

import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@EnableCaching
class MvcConfig : WebMvcConfigurer {
    // Remove the default ROLE_ prefix
    @Bean
    fun grantedAuthorityDefaults(): GrantedAuthorityDefaults {
        return GrantedAuthorityDefaults("")
    }
}
