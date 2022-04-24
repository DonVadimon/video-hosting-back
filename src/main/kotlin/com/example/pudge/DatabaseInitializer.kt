package com.example.pudge


import com.example.pudge.configuration.security.SecurityUtils
import com.example.pudge.domain.dto.CreateUserDto
import com.example.pudge.domain.dto.CreateVideoDto
import com.example.pudge.domain.entity.Authorities
import com.example.pudge.service.AuthorityService
import com.example.pudge.service.UserService
import com.example.pudge.service.VideoService
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component


@Component
class DatabaseInitializer(
    private val userService: UserService,
    private val authorityService: AuthorityService,
    private val videoService: VideoService,
    private val securityUtils: SecurityUtils
) : ApplicationListener<ApplicationReadyEvent?> {
    private val password = "root"

    private val defaultGroupName = "IU5"

    private val defaultUsers = listOf(
        CreateUserDto(
            username = "ordinary",
            name = "Ordinary User",
            password = password,
            passwordRepeat = password,
            authorities = setOf(Authorities.ORDINARY_USER.toString()),
            group = defaultGroupName
        ),
        CreateUserDto(
            username = "maker",
            name = "VideoMaker User",
            password = password,
            passwordRepeat = password,
            authorities = setOf(Authorities.VIDEO_CREATOR.toString())
        ),
        CreateUserDto(
            username = "admin",
            name = "Admin User",
            password = password,
            passwordRepeat = password,
            authorities = setOf(Authorities.ADMIN.toString())
        ),
    )

    private val defaultVideo = CreateVideoDto(
        name = "Rick Astley - Never Gonna Give You Up",
        description = "“Never Gonna Give You Up” was a global smash on its release in July 1987, topping the charts in 25 countries including Rick’s native UK and the US Billboard Hot 100.",
        source = "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
        author = "admin",
        allowedGroups = mutableSetOf(defaultGroupName)
    )

    override fun onApplicationEvent(applicationReadyEvent: ApplicationReadyEvent) {
        securityUtils.createAnonymousContext()

        authorityService.createRoles()
        for (userDto in defaultUsers) {
            userService.upsert(userDto)
        }
        videoService.upsert(defaultVideo)

        securityUtils.clearContext()
    }
}
