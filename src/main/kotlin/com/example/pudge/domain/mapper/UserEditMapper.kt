package com.example.pudge.domain.mapper


import com.example.pudge.domain.dto.CreateUserDto
import com.example.pudge.domain.dto.UpdateUserDto
import com.example.pudge.domain.entity.Authorities
import com.example.pudge.domain.entity.UserEntity
import com.example.pudge.service.AuthorityService
import org.mapstruct.*
import org.springframework.beans.factory.annotation.Autowired
import java.util.stream.Collectors


@Mapper(componentModel = "spring")
abstract class UserEditMapper {

    private var authorityService: AuthorityService? = null

    @Autowired
    fun setAuthorityService(authorityService: AuthorityService) {
        this.authorityService = authorityService
    }

    @Mapping(target = "authorities", ignore = true)
    abstract fun createUserDtoToUserEntity(request: CreateUserDto?): UserEntity?

    @AfterMapping
    protected open fun afterCreateUserDtoToUserEntity(
        request: CreateUserDto, @MappingTarget user: UserEntity
    ) {
        if (request.authorities != null) {
            user.authorities =
                (request.authorities!!.stream().map { authorityService!!.getByName(Authorities.valueOf(it!!)) }
                    .collect(Collectors.toSet()))
        }
    }

    @BeanMapping(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "authorities", ignore = true)
    abstract fun updateUserDtoToUserEntity(
        request: UpdateUserDto?, @MappingTarget user: UserEntity?
    )

    @AfterMapping
    protected open fun afterUpdateUserDtoToUserEntity(
        request: UpdateUserDto, @MappingTarget user: UserEntity
    ) {
        if (request.authorities != null) {
            user.authorities =
                (request.authorities!!.stream().map { authorityService!!.getByName(Authorities.valueOf(it!!)) }
                    .collect(Collectors.toSet()))
        }
    }
}
