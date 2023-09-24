package com.team.testtask.network

import com.team.testtask.network.responses.GifSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GifService {
    @GET("search")
    suspend fun search(
        @Query("api_key") apiKey: String,
        @Query("q") keyWord: String,
        @Query("limit") limit: String  = "25",
        @Query("offset") offset: String  = "0",
        @Query("rating") rating: String  = "g",
        @Query("lang") lang: String  = "eng",
    ): GifSearchResponse
}