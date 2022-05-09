package com.example.pudge.api

import com.example.pudge.domain.entity.ConstAuthorities
import com.example.pudge.service.StorageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping(path = ["storage"])
class StorageApi(
    private var storageService: StorageService
) {
    @GetMapping("test")
    @RolesAllowed(ConstAuthorities.ADMIN.name, ConstAuthorities.VIDEO_CREATOR.name)
    fun helloAdmin() = ResponseEntity.ok("Hello, Admin")

    @GetMapping("{bucket}")
    @RolesAllowed(ConstAuthorities.ADMIN.name, ConstAuthorities.VIDEO_CREATOR.name)
    fun getFileList(
        @PathVariable("bucket") bucket: String
    ): ResponseEntity<List<String>> {
        return ResponseEntity.of(Optional.of(storageService.getBucketFileList(bucket)))
    }
}
