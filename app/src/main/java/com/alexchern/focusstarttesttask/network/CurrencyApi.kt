package com.alexchern.focusstarttesttask.network

import com.alexchern.focusstarttesttask.data.models.ApiResponse
import retrofit2.http.GET

interface CurrencyApi {

    @GET("daily_json.js")
    suspend fun getCurrencies() : ApiResponse
}