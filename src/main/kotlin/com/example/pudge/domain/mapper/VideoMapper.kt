package com.example.pudge.domain.mapper

import com.example.pudge.domain.dto.CreateVideoDto
import com.example.pudge.domain.dto.UpdateVideoDto
import com.example.pudge.domain.entity.VideoEntity
import com.example.pudge.service.UserGroupService
import com.example.pudge.service.UserService
import org.mapstruct.*
import org.springframework.beans.factory.annotation.Autowired
import java.util.stream.Collectors

@Mapper(componentModel = "spring")
abstract class VideoMapper {
    private var userService: UserService? = null

    private var userGroupService: UserGroupService? = null

    @Autowired
    fun setUserService(userService: UserService) {
        this.userService = userService
    }

    @Autowired
    fun setUserGroupService(userGroupService: UserGroupService) {
        this.userGroupService = userGroupService
    }

    private fun mapAllowedGroups(allowedGroups: MutableSet<String>?, video: VideoEntity) {
        if (allowedGroups !== null) {
            video.allowedGroups =
                allowedGroups.stream().map { userGroupService!!.getByNameOrCreate(it) }.collect(Collectors.toSet())
        }
    }

    private fun mapAuthor(authorId: Long?, video: VideoEntity) {
        if (authorId !== null) {
            video.author = userService!!.getById(authorId)
        }
    }

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "allowedGroups", ignore = true)
    abstract fun createVideoDtoToVideoEntity(dto: CreateVideoDto?): VideoEntity?

    @AfterMapping
    protected open fun afterCreateVideoDtoToVideoEntity(
        dto: CreateVideoDto, @MappingTarget video: VideoEntity
    ) {
        mapAuthor(dto.author, video)
        mapAllowedGroups(dto.allowedGroups, video)
    }

    @BeanMapping(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "allowedGroups", ignore = true)
    abstract fun updateVideoDtoToVideoEntity(
        dto: UpdateVideoDto?, @MappingTarget video: VideoEntity?
    )

    @AfterMapping
    protected open fun afterUpdateVideoDtoToVideoEntity(
        dto: UpdateVideoDto, @MappingTarget video: VideoEntity
    ) = mapAllowedGroups(dto.allowedGroups, video)
}
