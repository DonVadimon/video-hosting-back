package com.example.pudge.domain.entity

import javax.persistence.*

@Entity
@Table(name = "video_entity")
class VideoEntity(
    @Column(length = 2048, unique = true, nullable = false) var name: String,
    @Column(columnDefinition = "TEXT", nullable = false) var description: String,
    @Column(length = 4096, nullable = false) var source: String,
    @ManyToOne @JoinColumn(name = "author_id", nullable = false) var author: UserEntity,
    @ManyToMany(fetch = FetchType.EAGER) @JoinTable(
        name = "video_groups",
        joinColumns = [JoinColumn(name = "video_id")],
        inverseJoinColumns = [JoinColumn(name = "group_id")]
    ) var allowedGroups: MutableSet<UserGroupEntity> = HashSet(),
) : BaseEntity<Long>()
