package com.example.pudge.users

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(path = "users")
interface UserRepository : PagingAndSortingRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
}
