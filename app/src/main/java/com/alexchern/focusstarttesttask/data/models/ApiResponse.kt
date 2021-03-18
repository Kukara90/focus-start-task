package com.alexchern.focusstarttesttask.data.models

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("Date")
    val date: String,

    @SerializedName("PreviousDate")
    val previousDate: String,

    @SerializedName("PreviousURL")
    val previousURL: String,

    @SerializedName("Timestamp")
    val timestamp: String,

    @SerializedName("Valute")
    val currency: Map<String, Currency>
)