package com.example.pudge


import com.example.pudge.domain.dto.CreateUserDto
import com.example.pudge.domain.dto.CreateVideoDto
import com.example.pudge.domain.entity.Authorities
import com.example.pudge.domain.entity.ConstAuthorities
import com.example.pudge.service.AuthorityService
import com.example.pudge.service.UserService
import com.example.pudge.service.VideoService
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
class DatabaseInitializer(
    private val userService: UserService,
    private val authorityService: AuthorityService,
    private val videoService: VideoService
) : ApplicationListener<ApplicationReadyEvent?> {
    private val password = "root"

    private val defaultGroupName = "IU5"

    private val defaultUsers = listOf(
        CreateUserDto(
            "ordinary", password, password, setOf(Authorities.ORDINARY_USER.toString()), defaultGroupName
        ),
        CreateUserDto(
            "maker", password, password, setOf(Authorities.VIDEO_CREATOR.toString())
        ),
        CreateUserDto(
            "admin", password, password, setOf(Authorities.ADMIN.toString())
        ),
    )

    private val defaultVideo = CreateVideoDto(
        name = "Rick Astley - Never Gonna Give You Up",
        description = "“Never Gonna Give You Up” was a global smash on its release in July 1987, topping the charts in 25 countries including Rick’s native UK and the US Billboard Hot 100.",
        source = "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
        author = 3,
        allowedGroups = mutableSetOf(defaultGroupName)
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
        videoService.upsert(defaultVideo)

        SecurityContextHolder.clearContext()
    }
}
