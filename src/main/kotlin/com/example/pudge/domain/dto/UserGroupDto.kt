package com.example.pudge.domain.dto

import org.jetbrains.annotations.NotNull
import javax.validation.constraints.NotBlank

data class UserGroupDto(@field:NotNull @field:NotBlank val name: String) : java.io.Serializable
