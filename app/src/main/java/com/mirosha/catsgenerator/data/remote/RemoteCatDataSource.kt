package com.mirosha.catsgenerator.data.remote

import javax.inject.Inject

class RemoteCatDataSource
@Inject constructor(private val catService: CatService) {

    suspend fun getAllTags() = catService.getAllTags()

    suspend fun getRandomCat() = catService.getRandomCat()

    suspend fun getCatByOptions(
        tag: String?,
        text: String?,
        options: HashMap<String?, String?>?
    ) = catService.getCatByOptions(tag, text, options)
}