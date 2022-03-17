package com.example.pudge


import com.example.pudge.domain.dto.CreateUserDto
import com.example.pudge.domain.entity.Authorities
import com.example.pudge.service.AuthorityService
import com.example.pudge.service.UserService
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class DatabaseInitializer(private val userService: UserService, private val authorityService: AuthorityService) :
    ApplicationListener<ApplicationReadyEvent?> {
    private val password = "root"

    private val defaultUsers = listOf(
        CreateUserDto(
            "ordinary", password, password, setOf(Authorities.ORDINARY_USER.toString())
        ),
        CreateUserDto(
            "maker", password, password, setOf(Authorities.VIDEO_CREATOR.toString())
        ),
        CreateUserDto(
            "admin", password, password, setOf(Authorities.ADMIN.toString())
        ),
    )

    override fun onApplicationEvent(applicationReadyEvent: ApplicationReadyEvent) {
        authorityService.createRoles()
        for (userDto in defaultUsers) {
            userService.upsert(userDto)
        }
    }
}
