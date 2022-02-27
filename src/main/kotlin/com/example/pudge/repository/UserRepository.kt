package com.example.pudge.repository

import com.example.pudge.domain.entity.UserEntity
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(path = "users")
interface UserRepository : PagingAndSortingRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
}
