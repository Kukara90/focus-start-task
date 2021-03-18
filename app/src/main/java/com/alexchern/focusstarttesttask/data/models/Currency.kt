package com.alexchern.focusstarttesttask.data.models

import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("ID")
    val id: String,

    @SerializedName("NumCode")
    val numCode: String,

    @SerializedName("CharCode")
    val charCode: String,

    @SerializedName("Nominal")
    val nominal: Long,

    @SerializedName("Name")
    val name: String,

    @SerializedName("Value")
    val value: Double,

    @SerializedName("Previous")
    val previous: Double
)
