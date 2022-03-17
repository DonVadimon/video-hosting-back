package com.example.pudge.repository

import com.example.pudge.domain.entity.AuthorityEntity
import com.example.pudge.domain.entity.Authorities
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorityRepository : JpaRepository<AuthorityEntity, Long> {
    fun findByName(name: Authorities): AuthorityEntity?
}
