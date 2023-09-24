package com.team.testtask.di

import com.team.testtask.network.GifService
import com.team.testtask.repository.ImageRepo
import com.team.testtask.utils.mapper.GifDataMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideImageRepository(
        imageService: GifService,
        imageDataMapper: GifDataMapper
    ): ImageRepo {
        return ImageRepo(
            imageService,
            imageDataMapper
        )
    }
}