package com.mirosha.catsgenerator.data.remote

import com.mirosha.catsgenerator.model.CatResponse
import com.mirosha.catsgenerator.utils.UrlConstants.RANDOM_CAT_URL
import retrofit2.Response
import retrofit2.http.GET

interface CatService {

    @GET(RANDOM_CAT_URL)
    suspend fun getRandomCat(): Response<CatResponse>
}