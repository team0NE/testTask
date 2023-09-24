package com.team.testtask.repository

import com.team.testtask.domain.model.GifImage
import com.team.testtask.network.GifService
import com.team.testtask.utils.mapper.GifDataMapper

class ImageRepo(
    private val imageService: GifService,
    private val mapper: GifDataMapper
) {
    suspend fun search(apiKey: String, key: String): List<GifImage> {
        val result = imageService.search(apiKey = apiKey, keyWord = key).data
        return mapper.toDomainList(result)
    }
}