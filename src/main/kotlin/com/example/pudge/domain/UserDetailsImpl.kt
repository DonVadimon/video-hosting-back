package com.example.pudge.domain

import com.example.pudge.domain.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import java.util.stream.Collectors

class UserDetailsImpl(
    private var id: Long,
    private var username: String,
    private var password: String,
    authorities: List<GrantedAuthority>?
) : UserDetails {

    private lateinit var authorities: List<GrantedAuthority>

    init {
        if (authorities != null) {
            this.authorities = authorities
        }
    }

    companion object {
        fun build(user: UserEntity): UserDetailsImpl {
            val authorities: List<SimpleGrantedAuthority>? =
                user.authorities.stream().map { authorityEntity -> SimpleGrantedAuthority(authorityEntity.name.name) }
                    .collect(Collectors.toList())
            return UserDetailsImpl(
                user.id ?: 0, user.username, user.password, authorities
            )
        }
    }

    override fun getAuthorities(): List<GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    fun getId(): Long {
        return id
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val user = other as UserDetailsImpl
        return Objects.equals(id, user.id)
    }
}
