package com.example.pudge.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import java.time.Duration


@Configuration
class S3Config {
    @Bean(destroyMethod = "close")
    fun s3Client() : S3Client {
        val regions: MutableList<Region> = Region.regions()
        val region = Region.EU_NORTH_1
        return S3Client
            .builder()
            .region(region)
            .build()
    }
}