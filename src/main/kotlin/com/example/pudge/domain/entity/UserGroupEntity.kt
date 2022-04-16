package com.example.pudge.domain.entity

import javax.persistence.*

@Entity
@Table(name = "user_group_entity")
class UserGroupEntity(
    @Column(length = 100, unique = true) val name: String, @OneToMany(
        mappedBy = "group", fetch = FetchType.LAZY, orphanRemoval = true, cascade = [CascadeType.ALL]
    ) val users: MutableList<UserEntity> = mutableListOf()
) : BaseEntity<Long>() {
    fun addUser(block: UserGroupEntity.() -> UserEntity) {
        users.add(block())
    }

    fun setUsers(block: UserGroupEntity.() -> MutableSet<UserEntity>) {
        users.clear()
        users.addAll(block())
    }
}
