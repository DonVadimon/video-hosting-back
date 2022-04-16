package com.example.pudge.repository

import com.example.pudge.domain.entity.ConstAuthorities
import com.example.pudge.domain.entity.UserGroupEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.security.access.prepost.PreAuthorize

@RepositoryRestResource(path = "user-groups")
@PreAuthorize("hasRole('${ConstAuthorities.VIDEO_CREATOR.name}') or hasRole('${ConstAuthorities.ADMIN.name}')")
interface UserGroupRepository : CrudRepository<UserGroupEntity, Long> {
    fun findByName(name: String): UserGroupEntity?
}
