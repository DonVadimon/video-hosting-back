package com.example.pudge.repository

import com.example.pudge.domain.entity.ConstAuthorities
import com.example.pudge.domain.entity.UserEntity
import com.example.pudge.domain.projection.UserProjection
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.security.access.prepost.PreAuthorize

@RepositoryRestResource(path = "users", excerptProjection = UserProjection::class)
interface UserRepository : PagingAndSortingRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?

    fun findAllByGroup_Name(group: String): MutableIterable<UserEntity>

    fun findAllByGroupIsNull(): MutableIterable<UserEntity>

    @PreAuthorize("hasRole('${ConstAuthorities.ADMIN.name}')")
    override fun deleteById(id: Long)

    @PreAuthorize("hasRole('${ConstAuthorities.ADMIN.name}')")
    override fun delete(entity: UserEntity)

    @PreAuthorize("hasRole('${ConstAuthorities.ADMIN.name}')")
    override fun deleteAll(entities: MutableIterable<UserEntity>)

    @PreAuthorize("hasRole('${ConstAuthorities.ADMIN.name}')")
    override fun deleteAll()

    @PreAuthorize("hasRole('${ConstAuthorities.ADMIN.name}')")
    override fun deleteAllById(ids: MutableIterable<Long>)

    @PreAuthorize("hasRole('${ConstAuthorities.ADMIN.name}')")
    override fun <S : UserEntity?> save(entity: S): S

    @PreAuthorize("hasRole('${ConstAuthorities.ADMIN.name}')")
    override fun <S : UserEntity?> saveAll(entities: MutableIterable<S>): MutableIterable<S>
}
