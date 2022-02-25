package com.example.pudge

import com.example.pudge.security.JwtCsrfFilter
import com.example.pudge.security.JwtTokenRepository
import com.example.pudge.users.UserService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
class SpringSecurityConfig(
    private val userService: UserService,
    private val jwtTokenRepository: JwtTokenRepository,
    @Qualifier("handlerExceptionResolver") private val resolver: HandlerExceptionResolver
) : WebSecurityConfigurerAdapter() {
    @Bean
    fun devPasswordEncoder(): PasswordEncoder {
        return NoOpPasswordEncoder.getInstance()
    }

    @Throws(java.lang.Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.NEVER)
            .and()
            .addFilterAt(JwtCsrfFilter(jwtTokenRepository, resolver), CsrfFilter::class.java)
            .csrf().ignoringAntMatchers("/**")
            .and()
            .authorizeRequests()
            .antMatchers("/auth/login")
            .authenticated()
            .and()
            .httpBasic()
            .authenticationEntryPoint { request: HttpServletRequest?, response: HttpServletResponse?, e: AuthenticationException? ->
                resolver.resolveException(
                    request!!, response!!, null, e!!
                )
            }
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(this.userService)
    }
}
