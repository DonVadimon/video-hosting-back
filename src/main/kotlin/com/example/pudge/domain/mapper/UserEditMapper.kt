package com.example.pudge.domain.mapper


import com.example.pudge.domain.dto.CreateUserDto
import com.example.pudge.domain.dto.UpdateUserDto
import com.example.pudge.domain.entity.Authorities
import com.example.pudge.domain.entity.UserEntity
import com.example.pudge.service.AuthorityService
import com.example.pudge.service.UserGroupService
import org.mapstruct.*
import org.springframework.beans.factory.annotation.Autowired
import java.util.stream.Collectors


@Mapper(componentModel = "spring")
abstract class UserEditMapper {

    private var authorityService: AuthorityService? = null

    private var userGroupService: UserGroupService? = null

    @Autowired
    fun setAuthorityService(authorityService: AuthorityService) {
        this.authorityService = authorityService
    }

    @Autowired
    fun setUserGroupService(userGroupService: UserGroupService) {
        this.userGroupService = userGroupService
    }

    private fun mapForeignValues(dto: UserForeignFields, user: UserEntity) {
        if (dto.authorities != null) {
            user.authorities =
                (dto.authorities!!.stream().map { authorityService!!.getByName(Authorities.valueOf(it!!)) }
                    .collect(Collectors.toSet()))
        }
        if (dto.group != null) {
            user.group = this.userGroupService!!.getByNameOrCreate(dto.group!!)
        }
    }

    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "group", ignore = true)
    abstract fun createUserDtoToUserEntity(dto: CreateUserDto?): UserEntity?

    @AfterMapping
    protected open fun afterCreateUserDtoToUserEntity(
        dto: CreateUserDto, @MappingTarget user: UserEntity
    ) = mapForeignValues(dto, user)

    @BeanMapping(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "group", ignore = true)
    abstract fun updateUserDtoToUserEntity(
        dto: UpdateUserDto?, @MappingTarget user: UserEntity?
    )

    @AfterMapping
    protected open fun afterUpdateUserDtoToUserEntity(
        dto: UpdateUserDto, @MappingTarget user: UserEntity
    ) = mapForeignValues(dto, user)
}
