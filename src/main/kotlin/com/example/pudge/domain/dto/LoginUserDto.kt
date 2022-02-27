package com.example.pudge.domain.dto

import org.jetbrains.annotations.NotNull
import javax.validation.constraints.NotBlank

data class LoginUserDto(
    @get:NotNull @get:NotBlank var username: String? = "", @get:NotNull @get:NotBlank var password: String? = ""
) : java.io.Serializable
