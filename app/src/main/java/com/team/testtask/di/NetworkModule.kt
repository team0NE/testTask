package com.team.testtask.di

import com.google.gson.GsonBuilder
import com.team.testtask.network.GifService
import com.team.testtask.utils.apiKey
import com.team.testtask.utils.mapper.GifDataMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideImageMapper(): GifDataMapper {
        return GifDataMapper()
    }

    @Singleton
    @Provides
    fun provideImageService(): GifService {
        return Retrofit.Builder()
            .baseUrl("https://api.giphy.com/v1/gifs/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(GifService::class.java)
    }

    @Singleton
    @Provides
    @Named("api_key")
    fun provideAuthToken(): String {
        return apiKey
    }
}