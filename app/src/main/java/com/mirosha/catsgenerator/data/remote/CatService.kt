package com.mirosha.catsgenerator.data.remote

import com.mirosha.catsgenerator.model.CatResponse
import com.mirosha.catsgenerator.utils.Constants.CAT_BY_OPTIONS_URL
import com.mirosha.catsgenerator.utils.Constants.RANDOM_CAT_URL
import com.mirosha.catsgenerator.utils.Constants.TAGS_URL
import com.mirosha.catsgenerator.utils.Constants.TAG_PARAMETER
import com.mirosha.catsgenerator.utils.Constants.TEXT_PARAMETER
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface CatService {

    @GET(TAGS_URL)
    suspend fun getAllTags(): Response<MutableList<String>>

    @GET(RANDOM_CAT_URL)
    suspend fun getRandomCat(): Response<CatResponse>

    @GET(CAT_BY_OPTIONS_URL)
    suspend fun getCatByOptions(
        @Path(TAG_PARAMETER) tag: String?,
        @Path(TEXT_PARAMETER) text: String?,
        @QueryMap options: HashMap<String?, String?>?
    ): Response<CatResponse>
}
