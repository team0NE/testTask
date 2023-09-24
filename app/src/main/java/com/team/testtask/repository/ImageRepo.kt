package com.team.testtask.repository

import com.team.testtask.domain.model.GifImage
import com.team.testtask.network.GifService
import com.team.testtask.utils.mapper.GifDataMapper

class ImageRepo(
    private val imageService: GifService,
    private val mapper: GifDataMapper
) {
    // it's a good place for internet check and Room implementation
    // But it's only a test task for trainee and I choose to not over complicate it
    suspend fun search(apiKey: String, key: String): List<GifImage> {
        val result = imageService.search(apiKey = apiKey, keyWord = key).data
        return mapper.toDomainList(result)
    }
}