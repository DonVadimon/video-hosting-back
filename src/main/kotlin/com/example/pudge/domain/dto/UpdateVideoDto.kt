package com.example.pudge.domain.dto

data class UpdateVideoDto(
    var name: String?,
    var description: String?,
    var allowedGroups: MutableSet<String>? = HashSet(),
) : java.io.Serializable
