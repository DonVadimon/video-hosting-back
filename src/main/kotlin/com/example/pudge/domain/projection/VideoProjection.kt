package com.example.pudge.domain.projection

import com.example.pudge.domain.entity.UserEntity
import com.example.pudge.domain.entity.UserGroupEntity
import com.example.pudge.domain.entity.VideoEntity
import org.springframework.data.rest.core.config.Projection

@Projection(
    name = "videoProjection", types = [VideoEntity::class]
)
interface VideoProjection {
    fun getName(): String
    fun getDescription(): String
    fun getSource(): String
    fun getAuthor(): UserEntity
    fun getAllowedGroups(): List<UserGroupEntity>
}
