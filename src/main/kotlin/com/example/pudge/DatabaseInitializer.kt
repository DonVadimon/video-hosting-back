package com.example.pudge


import com.example.pudge.domain.dto.CreateUserDto
import com.example.pudge.domain.entity.Authorities
import com.example.pudge.domain.entity.ConstAuthorities
import com.example.pudge.service.AuthorityService
import com.example.pudge.service.UserService
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component


@Component
class DatabaseInitializer(private val userService: UserService, private val authorityService: AuthorityService) :
    ApplicationListener<ApplicationReadyEvent?> {
    private val password = "root"

    private val defaultUsers = listOf(
        CreateUserDto(
            "ordinary", password, password, setOf(Authorities.ORDINARY_USER.toString()), "IU5"
        ),
        CreateUserDto(
            "maker", password, password, setOf(Authorities.VIDEO_CREATOR.toString())
        ),
        CreateUserDto(
            "admin", password, password, setOf(Authorities.ADMIN.toString())
        ),
    )

    override fun onApplicationEvent(applicationReadyEvent: ApplicationReadyEvent) {
        val securityContext: SecurityContext = SecurityContextImpl()
        val user = User("initializer", "initializer", mutableSetOf())

        val grantedAuthorities: MutableCollection<GrantedAuthority> = ArrayList()
        grantedAuthorities.add(SimpleGrantedAuthority(ConstAuthorities.ORDINARY_USER.name))
        grantedAuthorities.add(SimpleGrantedAuthority(ConstAuthorities.VIDEO_CREATOR.name))
        grantedAuthorities.add(SimpleGrantedAuthority(ConstAuthorities.ADMIN.name))
        val anonymousAuthenticationToken = AnonymousAuthenticationToken("initializer", user, grantedAuthorities)
        securityContext.authentication = anonymousAuthenticationToken
        SecurityContextHolder.setContext(securityContext)


        authorityService.createRoles()
        for (userDto in defaultUsers) {
            userService.upsert(userDto)
        }

        SecurityContextHolder.clearContext()
    }
}
