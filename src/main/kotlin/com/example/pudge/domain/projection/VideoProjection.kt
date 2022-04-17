package com.example.pudge.domain.projection

import com.example.pudge.domain.entity.UserEntity
import com.example.pudge.domain.entity.VideoEntity
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.rest.core.config.Projection

@Projection(
    name = "videoAuthor", types = [VideoEntity::class]
)
interface AuthorProjection {
    @Value("#{target.id}")
    fun getId(): Long
    fun getAuthor(): UserEntity
}
