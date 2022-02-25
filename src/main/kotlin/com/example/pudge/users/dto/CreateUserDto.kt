package com.example.pudge.users.dto

import org.jetbrains.annotations.NotNull
import java.io.Serializable

data class CreateUserDto(@get:NotNull val username: String, @get:NotNull val password: String) : Serializable
