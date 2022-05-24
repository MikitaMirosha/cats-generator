package com.mirosha.catsgenerator.data.remote

import javax.inject.Inject

class RemoteCatDataSource
@Inject constructor(private val catService: CatService) {

    suspend fun getAllTags() = catService.getAllTags()

    suspend fun getRandomCat() = catService.getRandomCat()

    suspend fun getCatByTag(
        tag: String?,
        options: HashMap<String?, String?>?
    ) = catService.getCatByTag(tag, options)

    suspend fun getCatByText(
        text: String?,
        options: HashMap<String?, String?>?
    ) = catService.getCatByText(text, options)

    suspend fun getCatByTagAndText(
        tag: String?,
        text: String?,
        options: HashMap<String?, String?>?
    ) = catService.getCatByTagAndText(tag, text, options)
}