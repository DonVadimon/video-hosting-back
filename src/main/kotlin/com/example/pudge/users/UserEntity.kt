package com.example.pudge.users

import com.example.pudge.shared.BaseEntity
import javax.persistence.Entity

@Entity
class UserEntity(
    var username: String, var password: String
) : BaseEntity<Long>()
