package com.example.pudge.configuration.security

import com.example.pudge.domain.entity.ConstAuthorities
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component

@Component
class SecurityUtils {
    fun createAnonymousContext() {
        val securityContext: SecurityContext = SecurityContextImpl()
        val user = User("initializer", "initializer", mutableSetOf())

        val grantedAuthorities: MutableCollection<GrantedAuthority> = ArrayList()
        grantedAuthorities.add(SimpleGrantedAuthority(ConstAuthorities.ORDINARY_USER.name))
        grantedAuthorities.add(SimpleGrantedAuthority(ConstAuthorities.VIDEO_CREATOR.name))
        grantedAuthorities.add(SimpleGrantedAuthority(ConstAuthorities.ADMIN.name))
        val anonymousAuthenticationToken = AnonymousAuthenticationToken("initializer", user, grantedAuthorities)
        securityContext.authentication = anonymousAuthenticationToken

        SecurityContextHolder.setContext(securityContext)
    }

    fun clearContext() {
        SecurityContextHolder.clearContext()
    }
}
