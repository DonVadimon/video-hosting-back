package com.example.pudge.domain.dto

import org.jetbrains.annotations.NotNull
import java.io.Serializable
import javax.validation.constraints.NotBlank

data class CreateUserDto(
    @field:NotNull @field:NotBlank var username: String?,
    @field:NotNull @field:NotBlank var password: String?,
    @field:NotNull @field:NotBlank var passwordRepeat: String?,
    var authorities: Set<String>? = null
) : Serializable
