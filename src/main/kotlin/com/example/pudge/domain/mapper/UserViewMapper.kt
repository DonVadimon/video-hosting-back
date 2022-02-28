package com.example.pudge.domain.mapper

import com.example.pudge.domain.dto.UserView
import com.example.pudge.domain.entity.UserEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserViewMapper {
    fun toUserView(user: UserEntity?): UserView?
}
