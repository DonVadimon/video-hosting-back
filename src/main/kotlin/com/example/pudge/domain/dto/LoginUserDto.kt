package com.example.pudge.domain.dto

import org.jetbrains.annotations.NotNull

data class LoginUserDto(
    @get:NotNull var username: String? = "", @get:NotNull var password: String? = ""
) : java.io.Serializable
