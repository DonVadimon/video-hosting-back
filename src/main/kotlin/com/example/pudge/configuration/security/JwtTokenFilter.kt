package com.example.pudge.configuration.security


import com.example.pudge.repository.UserRepository
import com.example.pudge.domain.UserDetailsImpl
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtTokenFilter(
    private val jwtTokenUtil: JwtTokenUtil, private val userRepo: UserRepository
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain
    ) {
        // Get authorization header and validate
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (!StringUtils.hasLength(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response)
            return
        }

        // Get jwt token and validate
        val token = header.split(" ").toTypedArray()[1].trim { it <= ' ' }
        if (!jwtTokenUtil.validate(token)) {
            chain.doFilter(request, response)
            return
        }

        // Get user identity and set it on the spring security context
        val userDetails: UserDetails = UserDetailsImpl.build(userRepo.findByUsername(jwtTokenUtil.getUsername(token))!!)
        val authentication = UsernamePasswordAuthenticationToken(
            userDetails, null, Optional.ofNullable(userDetails).map { obj -> obj.authorities }.orElse(listOf())
        )
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }
}
