package com.alexchern.focusstarttesttask.network

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: String?
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()
}
