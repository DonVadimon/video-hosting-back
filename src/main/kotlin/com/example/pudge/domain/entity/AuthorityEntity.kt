package com.example.pudge.domain.entity

import org.springframework.security.core.GrantedAuthority
import javax.persistence.*


enum class Authorities {
    ORDINARY_USER, VIDEO_CREATOR, ADMIN
}

// Duplicate is required because of Kotlin specifics in const
sealed class ConstAuthorities {
    object ORDINARY_USER : ConstAuthorities() {
        const val name = "ORDINARY_USER"
    }

    object VIDEO_CREATOR : ConstAuthorities() {
        const val name = "VIDEO_CREATOR"
    }

    object ADMIN : ConstAuthorities() {
        const val name = "ADMIN"
    }
}

@Entity
@Table(name = "authority_entity")
class AuthorityEntity(
    @Enumerated(EnumType.STRING) @Column(length = 20) val name: Authorities,
) : BaseEntity<Long>(), GrantedAuthority {
    override fun getAuthority(): String {
        return this.name.toString()
    }
}
