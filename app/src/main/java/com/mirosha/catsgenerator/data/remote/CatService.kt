package com.mirosha.catsgenerator.data.remote

import com.mirosha.catsgenerator.model.CatResponse
import com.mirosha.catsgenerator.utils.Constants.CAT_BY_TAG_AND_TEXT_URL
import com.mirosha.catsgenerator.utils.Constants.CAT_BY_TAG_URL
import com.mirosha.catsgenerator.utils.Constants.CAT_BY_TEXT_URL
import com.mirosha.catsgenerator.utils.Constants.CAT_URL
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

    @GET(CAT_URL)
    suspend fun getRandomCat(): Response<CatResponse>

    @GET(CAT_BY_TAG_URL)
    suspend fun getCatByTag(
        @Path(TAG_PARAMETER) tag: String?,
        @QueryMap options: HashMap<String?, String?>?
    ): Response<CatResponse>

    @GET(CAT_BY_TEXT_URL)
    suspend fun getCatByText(
        @Path(TEXT_PARAMETER) text: String?,
        @QueryMap options: HashMap<String?, String?>?
    ): Response<CatResponse>

    @GET(CAT_BY_TAG_AND_TEXT_URL)
    suspend fun getCatByTagAndText(
        @Path(TAG_PARAMETER) tag: String?,
        @Path(TEXT_PARAMETER) text: String?,
        @QueryMap options: HashMap<String?, String?>?
    ): Response<CatResponse>
}
