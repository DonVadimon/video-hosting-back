package com.example.pudge.service

import com.example.pudge.domain.entity.Authorities
import com.example.pudge.domain.entity.AuthorityEntity
import com.example.pudge.repository.AuthorityRepository
import org.springframework.stereotype.Service

@Service
class AuthorityService(private val authorityRepository: AuthorityRepository) {
    fun createRoles() {
        for (authority in Authorities.values()) {
            val authorityEntity = authorityRepository.findByName(authority);
            if (authorityEntity == null) {
                authorityRepository.save(AuthorityEntity(authority))
            }
        }
    }

    fun getByName(name: Authorities) = authorityRepository.findByName(name)
}
