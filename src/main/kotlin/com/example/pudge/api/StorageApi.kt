package com.example.pudge.api

import com.example.pudge.domain.entity.ConstAuthorities
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping(path = ["storage"])
class StorageApi {
    @GetMapping
    @RolesAllowed(ConstAuthorities.ADMIN.name, ConstAuthorities.ORDINARY_USER.name)
    fun helloAdmin() = ResponseEntity.ok("Hello, Admin")
}
