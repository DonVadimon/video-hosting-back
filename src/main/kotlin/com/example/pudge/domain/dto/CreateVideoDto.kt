package com.example.pudge.domain.dto

import org.jetbrains.annotations.NotNull
import javax.validation.constraints.NotBlank

data class CreateVideoDto(
    @field:NotNull @field:NotBlank var name: String,
    @field:NotNull @field:NotBlank var description: String,
    @field:NotNull @field:NotBlank var source: String,
    @field:NotNull var author: String,
    @field:NotNull var allowedGroups: MutableSet<String> = HashSet(),
) : java.io.Serializable
