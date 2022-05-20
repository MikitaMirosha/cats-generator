package com.mirosha.catsgenerator.utils

sealed class NetworkStatus<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Loading<T> : NetworkStatus<T>()
    class Success<T>(data: T) : NetworkStatus<T>(data)
    class Error<T>(message: String, data: T? = null) : NetworkStatus<T>(data, message)
}