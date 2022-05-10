package com.example.pudge.domain

import org.apache.commons.io.FilenameUtils
import org.springframework.web.multipart.MultipartFile
import java.util.*

interface IS3Object {
    val key: String
}

class S3Object(private val file: MultipartFile): IS3Object {

    private val id: String

    init {
        this.id = UUID.randomUUID().toString()
    }

    val name get() = file.originalFilename

    val baseName: String get() = FilenameUtils.getBaseName(name)

    val extension: String get() = FilenameUtils.getExtension(name)

    // Should be unique in the bucket. Should have extension at the end so that it's properly
    //   visible / downloadable at the client.
    override val key get() = "${baseName}${id}.${extension}"

}
