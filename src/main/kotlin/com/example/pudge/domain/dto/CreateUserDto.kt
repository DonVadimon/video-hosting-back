package com.example.pudge.domain.dto

import org.jetbrains.annotations.NotNull
import java.io.Serializable

data class CreateUserDto(@get:NotNull var username: String, @get:NotNull var password: String) : Serializable
