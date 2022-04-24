package com.example.pudge.repository

import com.example.pudge.domain.entity.ConstAuthorities
import com.example.pudge.domain.entity.VideoEntity
import com.example.pudge.domain.projection.VideoProjection
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.security.access.prepost.PreAuthorize

@RepositoryRestResource(path = "videos", excerptProjection = VideoProjection::class)
interface VideoRepository : PagingAndSortingRepository<VideoEntity, Long> {
    fun findByName(name: String): VideoEntity?

    fun findByNameContainingIgnoreCase(searchName: String): VideoEntity?

    fun findAllByAuthor_Username(username: String): MutableIterable<VideoEntity>

    fun findALlByAuthor_Id(id: Long): MutableIterable<VideoEntity>

    @PreAuthorize("hasRole('${ConstAuthorities.VIDEO_CREATOR.name}') or hasRole('${ConstAuthorities.ADMIN.name}')")
    override fun deleteById(id: Long)

    @PreAuthorize("hasRole('${ConstAuthorities.VIDEO_CREATOR.name}') or hasRole('${ConstAuthorities.ADMIN.name}')")
    override fun delete(entity: VideoEntity)

    @PreAuthorize("hasRole('${ConstAuthorities.VIDEO_CREATOR.name}') or hasRole('${ConstAuthorities.ADMIN.name}')")
    override fun deleteAll(entities: MutableIterable<VideoEntity>)

    @PreAuthorize("hasRole('${ConstAuthorities.VIDEO_CREATOR.name}') or hasRole('${ConstAuthorities.ADMIN.name}')")
    override fun deleteAll()

    @PreAuthorize("hasRole('${ConstAuthorities.VIDEO_CREATOR.name}') or hasRole('${ConstAuthorities.ADMIN.name}')")
    override fun deleteAllById(ids: MutableIterable<Long>)

    @PreAuthorize("hasRole('${ConstAuthorities.VIDEO_CREATOR.name}') or hasRole('${ConstAuthorities.ADMIN.name}')")
    override fun <S : VideoEntity?> save(entity: S): S

    @PreAuthorize("hasRole('${ConstAuthorities.VIDEO_CREATOR.name}') or hasRole('${ConstAuthorities.ADMIN.name}')")
    override fun <S : VideoEntity?> saveAll(entities: MutableIterable<S>): MutableIterable<S>
}
