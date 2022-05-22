package com.example.pudge.domain.projection

import com.example.pudge.domain.entity.AuthorityEntity
import com.example.pudge.domain.entity.UserEntity
import com.example.pudge.domain.entity.UserGroupEntity
import org.springframework.data.rest.core.config.Projection

@Projection(
    name = "userProjection", types = [UserEntity::class]
)
interface UserProjection {
    fun getUsername(): String
    fun getName(): String
    fun getAuthorities(): List<AuthorityEntity>
    fun getGroup(): UserGroupEntity?
    fun getEnabled(): Boolean
    fun getAccountNonExpired(): Boolean
    fun getCredentialsNonExpired(): Boolean
    fun getAccountNonLocked(): Boolean
}
