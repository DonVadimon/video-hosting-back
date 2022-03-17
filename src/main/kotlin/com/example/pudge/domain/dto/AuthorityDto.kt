package com.example.pudge.domain.dto

import com.example.pudge.domain.entity.Authorities
import java.io.Serializable

data class AuthorityDto(val id: Long, val name: Authorities) : Serializable
