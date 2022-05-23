package com.mirosha.catsgenerator.data.remote

import com.mirosha.catsgenerator.model.CatResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CatService {

    @GET("/cat?json=true")
    suspend fun getRandomCat(): Response<CatResponse>

    @GET("/api/tags")
    suspend fun getAllTags(): Response<MutableList<String>>

    @GET("/cat/{tag}?json=true")
    suspend fun getRandomCatByTag(@Path("tag") tag: String): Response<CatResponse>
}