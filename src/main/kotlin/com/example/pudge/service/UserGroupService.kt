package com.example.pudge.service

import com.example.pudge.domain.entity.UserGroupEntity
import com.example.pudge.repository.UserGroupRepository
import org.springframework.stereotype.Service

@Service
class UserGroupService(private val userGroupRepository: UserGroupRepository) {
    fun getByNameOrCreate(name: String): UserGroupEntity {
        return userGroupRepository.findByName(name) ?: return userGroupRepository.save(UserGroupEntity(name))
    }
}
