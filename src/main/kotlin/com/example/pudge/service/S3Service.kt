package com.example.pudge.service

import com.example.pudge.configuration.security.SecurityUtils
import com.example.pudge.domain.UserDetailsImpl
import com.example.pudge.domain.dto.CreateUserDto
import com.example.pudge.domain.dto.UpdateUserDto
import com.example.pudge.domain.entity.UserEntity
import com.example.pudge.domain.exception.UserAlreadyExistException
import com.example.pudge.domain.exception.UserNotFoundException
import com.example.pudge.domain.mapper.UserEditMapper
import com.example.pudge.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Async
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.http.SdkHttpResponse
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request
import javax.validation.ValidationException


//import org.springframework.web.multipart.MultipartFile
//import software.amazon.awssdk.http.SdkHttpResponse

interface StorageService {

    fun getBucketFileList(bucketName: String) : List<String>

    fun downloadFile(bucketName: String, fileKey: String) : ByteArray

    fun deleteFile(bucketName: String, fileKey: String) : SdkHttpResponse

//    fun uploadFiles(bucketName: String, files: Array<MultipartFile>) : List<S3BulkResponseEntity>

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

    override fun deleteFile(bucketName: String, fileKey: String) : SdkHttpResponse {
        return s3Client.deleteObject(
            DeleteObjectRequest.builder().bucket(bucketName).key(fileKey).build()).sdkHttpResponse()
    }
}