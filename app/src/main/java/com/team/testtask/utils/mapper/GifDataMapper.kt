package com.team.testtask.utils.mapper

import com.team.testtask.domain.model.GifImage
import com.team.testtask.network.model.GifData

class GifDataMapper: DomainMapper<GifData, GifImage> {
    override fun mapToDomainModel(model: GifData): GifImage {
        return GifImage(
            id = model.id,
            title = model.title,
            originalUrl = model.images?.original?.url,
            fixWidthUrl = model.images?.fixedWidth?.url
        )
    }

    override fun mapFromDomainModel(domainModel: GifImage): GifData {
        return GifData(
            id = domainModel.id,
            title = domainModel.title,
        )
    }

    fun toDomainList(initial: List<GifData>): List<GifImage> {
        return initial.map {
            mapToDomainModel(it)
        }
    }

}