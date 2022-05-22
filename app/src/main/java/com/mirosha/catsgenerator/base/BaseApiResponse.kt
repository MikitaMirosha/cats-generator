package com.mirosha.catsgenerator.base

import com.mirosha.catsgenerator.utils.NetworkStatus
import retrofit2.Response
import java.net.UnknownHostException

abstract class BaseApiResponse {

    suspend fun <T> makeApiCall(call: suspend () -> Response<T>): NetworkStatus<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkStatus.Success(body)
                }
            }
            return getApiCallError("${response.code()}: ${response.message()}")
        } catch (e: UnknownHostException) {
            return getApiCallError(NO_INTERNET_CONNECTION)
        } catch (e: Exception) {
            return getApiCallError(e.message ?: e.toString())
        }
    }

    private fun <T> getApiCallError(message: String): NetworkStatus<T> =
        NetworkStatus.Error(API_CALL_ERROR + message)

    private companion object {
        const val API_CALL_ERROR = "API call is failed: "
        const val NO_INTERNET_CONNECTION = "No internet connection"
    }
}