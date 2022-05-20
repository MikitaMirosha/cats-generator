package com.mirosha.catsgenerator.data.remote

import javax.inject.Inject

class RemoteCatDataSource
@Inject constructor(private val catService: CatService) {

    suspend fun getRandomCat() = catService.getRandomCat()
}