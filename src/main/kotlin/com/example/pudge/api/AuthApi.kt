package com.example.pudge.api


import com.example.pudge.configuration.security.JwtTokenUtil
import com.example.pudge.domain.dto.CreateUserDto
import com.example.pudge.domain.dto.LoginUserDto
import com.example.pudge.domain.dto.UserView
import com.example.pudge.domain.entity.UserEntity
import com.example.pudge.domain.entity.toUserView
import com.example.pudge.domain.mapper.UserViewMapper
import com.example.pudge.service.UserService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.User
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(path = ["api/public"])
class AuthApi(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenUtil: JwtTokenUtil,
    private val userService: UserService,
    private val userViewMapper: UserViewMapper
) {
    @PostMapping("login")
    fun login(@Validated @RequestBody request: LoginUserDto?): ResponseEntity<UserView> {
        return try {
            val authenticate = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    request?.username, request?.password
                )
            )
            val user: User? = authenticate.principal as User?
            val userEntity = userService.getByUsername(user?.username ?: "")
            ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(userEntity!!))
                .body(userViewMapper.toUserView(userEntity))
        } catch (ex: BadCredentialsException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @PostMapping("register")
    fun register(@RequestBody @Validated request: CreateUserDto?): UserView {
        return userViewMapper.toUserView(userService.createUser(request))!!
    }
}
