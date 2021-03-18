package com.alexchern.focusstarttesttask.repository

import android.util.Log
import com.alexchern.focusstarttesttask.data.models.ApiResponse
import com.alexchern.focusstarttesttask.network.CurrencyApi
import com.alexchern.focusstarttesttask.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class CurrencyRepository(
    private val currencyApi: CurrencyApi
) {

    private val TAG = CurrencyRepository::class.java.simpleName

    suspend fun getCurrencies(): Resource<ApiResponse> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(currencyApi.getCurrencies())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Log.d(
                            TAG,
                            "http exception code: ${throwable.code()} message: ${throwable.message()}"
                        )

                        Resource.Failure(
                            false,
                            throwable.code(),
                            throwable.response()?.errorBody().toString()
                        )
                    }

                    else -> {
                        Log.d(TAG, "some exception message: ${throwable.message}")
                        Resource.Failure(true, null, null)
                    }
                }
            }
        }
    }
}