package com.example.pudge.domain.dto

import com.example.pudge.domain.mapper.UserForeignFields

data class UpdateUserDto(
    val name: String?, override var authorities: Set<String?>? = null, override var group: String? = null
) : java.io.Serializable, UserForeignFields
