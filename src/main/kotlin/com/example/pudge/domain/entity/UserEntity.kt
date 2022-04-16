package com.example.pudge.domain.entity

import org.springframework.security.core.userdetails.User
import javax.persistence.*


@Entity
@Table(name = "user_entity")
class UserEntity(
    var username: String = "",
    var password: String = "",
    var enabled: Boolean = true,
    var accountNonExpired: Boolean = true,
    var credentialsNonExpired: Boolean = true,
    var accountNonLocked: Boolean = true,
    @ManyToMany(fetch = FetchType.EAGER) @JoinTable(
        name = "user_authorities",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "authority_id")]
    ) var authorities: MutableSet<AuthorityEntity> = HashSet(),
    @ManyToOne @JoinColumn(name = "user_group_entity_id", nullable = true)
    var group: UserGroupEntity? = null
) : BaseEntity<Long>() {
    //Convert this class to Spring Security's User object
    fun toUser() = User(
        username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities
    )
}
