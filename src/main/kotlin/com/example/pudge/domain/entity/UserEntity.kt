package com.example.pudge.domain.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import javax.persistence.Entity


@Entity
class UserEntity(
    var username: String = "",
    var password: String = "",
    var enabled: Boolean = true,
    var accountNonExpired: Boolean = true,
    var credentialsNonExpired: Boolean = true,
    var accountNonLocked: Boolean = true,
) : BaseEntity<Long>() {
    //Convert this class to Spring Security's User object
    fun toUser(): User {
        val authorities = mutableSetOf<GrantedAuthority>()
        return User(
            username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities
        )
    }
}
