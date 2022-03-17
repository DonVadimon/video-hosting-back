package com.example.pudge.domain.dto

data class UpdateUserDto(
    var authorities: Set<String?>? = null
) : java.io.Serializable
