package com.example.pudge.service

import com.example.pudge.domain.dto.CreateVideoDto
import com.example.pudge.domain.dto.UpdateVideoDto
import com.example.pudge.domain.entity.VideoEntity
import com.example.pudge.domain.mapper.VideoMapper
import com.example.pudge.repository.VideoRepository
import org.springframework.stereotype.Service


@Service
class VideoService(private val videoRepository: VideoRepository, private val videoMapper: VideoMapper) {
    fun upsert(dto: CreateVideoDto): VideoEntity? {
        val optionalVideo = videoRepository.findByName(dto.name)
        return if (optionalVideo == null) {
            videoRepository.save(videoMapper.createVideoDtoToVideoEntity(dto)!!)
        } else {
            val updateVideoDto =
                UpdateVideoDto(name = dto.name, description = dto.description, allowedGroups = dto.allowedGroups)
            videoMapper.updateVideoDtoToVideoEntity(updateVideoDto, optionalVideo)
            videoRepository.save(optionalVideo)
        }
    }
}
