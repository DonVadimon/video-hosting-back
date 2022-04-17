package com.example.pudge.repository

import com.example.pudge.domain.entity.ConstAuthorities
import com.example.pudge.domain.entity.UserGroupEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.security.access.prepost.PreAuthorize


@RepositoryRestResource(path = "user-groups")
interface UserGroupRepository : CrudRepository<UserGroupEntity, Long> {
    fun findByName(name: String): UserGroupEntity?

    @PreAuthorize("hasRole('${ConstAuthorities.VIDEO_CREATOR.name}') or hasRole('${ConstAuthorities.ADMIN.name}')")
    override fun deleteById(id: Long)

    @PreAuthorize("hasRole('${ConstAuthorities.VIDEO_CREATOR.name}') or hasRole('${ConstAuthorities.ADMIN.name}')")
    override fun delete(entity: UserGroupEntity)

    @PreAuthorize("hasRole('${ConstAuthorities.VIDEO_CREATOR.name}') or hasRole('${ConstAuthorities.ADMIN.name}')")
    override fun deleteAll(entities: MutableIterable<UserGroupEntity>)

    @PreAuthorize("hasRole('${ConstAuthorities.VIDEO_CREATOR.name}') or hasRole('${ConstAuthorities.ADMIN.name}')")
    override fun deleteAll()

    @PreAuthorize("hasRole('${ConstAuthorities.VIDEO_CREATOR.name}') or hasRole('${ConstAuthorities.ADMIN.name}')")
    override fun deleteAllById(ids: MutableIterable<Long>)

    @PreAuthorize("hasRole('${ConstAuthorities.VIDEO_CREATOR.name}') or hasRole('${ConstAuthorities.ADMIN.name}')")
    override fun <S : UserGroupEntity?> save(entity: S): S

    @PreAuthorize("hasRole('${ConstAuthorities.VIDEO_CREATOR.name}') or hasRole('${ConstAuthorities.ADMIN.name}')")
    override fun <S : UserGroupEntity?> saveAll(entities: MutableIterable<S>): MutableIterable<S>
}
