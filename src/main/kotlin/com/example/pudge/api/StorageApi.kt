package com.example.pudge.api

import com.example.pudge.domain.entity.ConstAuthorities
import com.example.pudge.service.StorageService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
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

    @DeleteMapping("{bucket}/{file}")
    fun deleteFile(
        @PathVariable("bucket") bucket: String,
        @PathVariable("file") fileKey: String): ResponseEntity<Any?> {
        val responseEntity = storageService.deleteFile(bucket, fileKey)
        return ResponseEntity(responseEntity, HttpStatus.valueOf(responseEntity.statusCode()))
    }

    @GetMapping("{bucket}/{file}")
    fun downloadFile(
        @PathVariable("bucket") bucket: String,
        @PathVariable("file") fileKey: String): ResponseEntity<ByteArray> {
        val sourceFile : ByteArray = storageService.downloadFile(bucket, fileKey)
        return ResponseEntity.ok().contentLength(sourceFile.size.toLong())
            .header("Content-type", "application/octet-stream")
            .header("Content-disposition", "attachment; filename=\"$fileKey\"")
            .body(sourceFile)
    }

    @GetMapping("{bucket}/{file}/url")
    fun getFileUrl(
        @PathVariable("bucket") bucket: String,
        @PathVariable("file") fileKey: String): ResponseEntity<String> {
        return ResponseEntity.of(Optional.of(storageService.getFileUrl(bucket, fileKey)))
    }

    @PostMapping("{bucket}")
    fun uploadFile(
        @PathVariable("bucket") bucket: String,
        @RequestPart("file") file : MultipartFile
    ): ResponseEntity<Any?> {
        val awsResponse = storageService.uploadFile(bucket, file)
        return ResponseEntity(awsResponse, HttpStatus.OK)
    }

}
