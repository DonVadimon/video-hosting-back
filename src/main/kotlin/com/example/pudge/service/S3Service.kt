package com.example.pudge.service

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.http.SdkHttpResponse
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.util.*


interface StorageService {

    // Bucket manipulations.
    fun getBucketFileList(bucketName: String) : List<String>

    // File manipulations.
    fun downloadFile(bucketName: String, fileKey: String) : ByteArray

    fun getFileUrl(bucketName: String, fileKey: String) : String

    fun deleteFile(bucketName: String, fileKey: String) : SdkHttpResponse

    fun uploadFile(bucketName: String, file: MultipartFile) : String

}

@Service
class S3Service(var s3Client: S3Client) : StorageService {

    override fun getBucketFileList(bucketName: String): List<String> {
        return s3Client
            .listObjectsV2(ListObjectsV2Request.builder().bucket(bucketName).build()).contents()
            .map { s3Object -> s3Object.key() }
    }

    @Async
    override fun downloadFile(bucketName: String, fileKey: String) : ByteArray {
        return s3Client.getObject(
            GetObjectRequest.builder().bucket(bucketName).key(fileKey).build()).readAllBytes()
    }

    @Async
    override fun getFileUrl(bucketName: String, fileKey: String) : String {
        return s3Client.utilities().getUrl { builder -> builder.bucket(bucketName).key(fileKey) }
            .toExternalForm()
    }

    override fun deleteFile(bucketName: String, fileKey: String) : SdkHttpResponse {
        return s3Client.deleteObject(
            DeleteObjectRequest.builder().bucket(bucketName).key(fileKey).build()).sdkHttpResponse()
    }

    @Async
    override fun uploadFile(bucketName: String, file: MultipartFile) : String {
        val fileKey = file.name + UUID.randomUUID().toString()
        val response = s3Client.putObject(PutObjectRequest.builder()
            .bucket(bucketName)
            .key(fileKey)
            .build(),
            RequestBody.fromBytes(file.bytes)
        ).sdkHttpResponse()

        return getFileUrl(bucketName, fileKey)
    }

}
