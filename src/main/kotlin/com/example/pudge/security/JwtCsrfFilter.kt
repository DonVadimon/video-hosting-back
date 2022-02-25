package com.example.pudge.security

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.security.web.csrf.CsrfTokenRepository
import org.springframework.security.web.csrf.InvalidCsrfTokenException
import org.springframework.security.web.csrf.MissingCsrfTokenException
import org.springframework.security.web.util.UrlUtils
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtCsrfFilter(private val tokenRepository: CsrfTokenRepository, private val resolver: HandlerExceptionResolver) :
    OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {
        request.setAttribute(HttpServletResponse::class.java.name, response)
        var csrfToken: CsrfToken? = tokenRepository.loadToken(request)
        val missingToken = csrfToken == null
        if (missingToken) {
            csrfToken = tokenRepository.generateToken(request)
            tokenRepository.saveToken(csrfToken, request, response)
        }
        request.setAttribute(CsrfToken::class.java.name, csrfToken)
        request.setAttribute(csrfToken?.parameterName, csrfToken)
        if (request.servletPath == "/auth/login") {
            try {
                filterChain.doFilter(request, response)
            } catch (e: Exception) {
                resolver.resolveException(request, response, null, MissingCsrfTokenException(csrfToken?.token))
            }
        } else {
            var actualToken = request.getHeader(csrfToken?.headerName)
            if (actualToken == null) {
                actualToken = request.getParameter(csrfToken?.parameterName)
            }
            try {
                if (!StringUtils.isEmpty(actualToken)) {
                    Jwts.parser().setSigningKey((tokenRepository as JwtTokenRepository).secret)
                        .parseClaimsJws(actualToken)
                    filterChain.doFilter(request, response)
                } else resolver.resolveException(
                    request, response, null, InvalidCsrfTokenException(csrfToken, actualToken)
                )
            } catch (e: JwtException) {
                if (logger.isDebugEnabled) {
                    logger.debug("Invalid CSRF token found for " + UrlUtils.buildFullRequestUrl(request))
                }
                if (missingToken) {
                    resolver.resolveException(request, response, null, MissingCsrfTokenException(actualToken))
                } else {
                    resolver.resolveException(
                        request, response, null, InvalidCsrfTokenException(csrfToken, actualToken)
                    )
                }
            }
        }
    }
}
