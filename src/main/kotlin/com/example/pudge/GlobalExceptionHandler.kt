package com.example.pudge

import com.example.pudge.security.JwtTokenRepository
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.session.SessionAuthenticationException
import org.springframework.security.web.csrf.InvalidCsrfTokenException
import org.springframework.security.web.csrf.MissingCsrfTokenException
import org.springframework.security.web.util.UrlUtils
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestControllerAdvice
class GlobalExceptionHandler(private val tokenRepository: JwtTokenRepository) : ResponseEntityExceptionHandler() {
    data class ErrorInfo(
        val url: String, val info: String
    )

    @ExceptionHandler(
        AuthenticationException::class,
        MissingCsrfTokenException::class,
        InvalidCsrfTokenException::class,
        SessionAuthenticationException::class
    )
    fun handleAuthenticationException(
        ex: RuntimeException?, request: HttpServletRequest?, response: HttpServletResponse
    ): ErrorInfo? {
        tokenRepository.clearToken(response)
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        return ErrorInfo(UrlUtils.buildFullRequestUrl(request), ex?.message ?: "")
    }
}
