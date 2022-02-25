package com.example.pudge.security

import com.example.pudge.users.UserEntity
import com.example.pudge.users.UserService
import com.example.pudge.users.dto.UserModel
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
@RequestMapping("auth")
class AuthController(private val userService: UserService) {
    @PostMapping("login")
    @ResponseBody
    fun getAuthUser(): UserEntity? {
        val auth = SecurityContextHolder.getContext().authentication ?: return null
        val principal = auth.principal
        val user = if (principal is UserModel) principal else null
        return (if (Objects.nonNull(user)) this.userService.getByUsername(user!!.username) else null)!!
    }
}
