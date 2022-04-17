package com.example.pudge.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.userdetails.User
import javax.persistence.*


@Entity
@Table(name = "user_entity")
class UserEntity(
    @Column(length = 512, unique = true, nullable = false)
    var username: String = "",
    @JsonIgnore var password: String = "",
    var enabled: Boolean = true,
    var accountNonExpired: Boolean = true,
    var credentialsNonExpired: Boolean = true,
    var accountNonLocked: Boolean = true,
    @ManyToMany(fetch = FetchType.EAGER) @JoinTable(
        name = "user_authorities",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "authority_id")]
    ) var authorities: MutableSet<AuthorityEntity> = HashSet(),
    @ManyToOne @JoinColumn(name = "user_group_entity_id", nullable = true) var group: UserGroupEntity? = null,
    @Column(length = 512) var name: String = "",
    @OneToMany(
        mappedBy = "author", fetch = FetchType.LAZY, orphanRemoval = true, cascade = [CascadeType.ALL]
    ) val videos: MutableList<VideoEntity> = mutableListOf()
) : BaseEntity<Long>() {
    //Convert this class to Spring Security's User object
    fun toUser() = User(
        username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities
    )
}
