package com.mirosha.catsgenerator.repo

import com.mirosha.catsgenerator.base.BaseApiResponse
import com.mirosha.catsgenerator.data.remote.RemoteCatDataSource
import com.mirosha.catsgenerator.model.CatResponse
import com.mirosha.catsgenerator.utils.NetworkStatus
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class CatRepository
@Inject constructor(
    private val remoteCatDataSource: RemoteCatDataSource
) : BaseApiResponse() {

    suspend fun getRandomCat(): Flow<NetworkStatus<CatResponse>> {
        return flow {
            emit(makeApiCall { remoteCatDataSource.getRandomCat() })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllTags(): Flow<NetworkStatus<MutableList<String>>> {
        return flow {
            emit(makeApiCall { remoteCatDataSource.getAllTags() })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getRandomCatByTag(tag: String): Flow<NetworkStatus<CatResponse>> {
        return flow {
            emit(makeApiCall { remoteCatDataSource.getRandomCatByTag(tag) })
        }.flowOn(Dispatchers.IO)
    }
}