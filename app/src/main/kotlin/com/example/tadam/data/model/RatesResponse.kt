package com.example.tadam.data.model

import com.google.gson.annotations.SerializedName

data class RatesResponse(
    @SerializedName("base") val base: String,
    @SerializedName("date") val date: String,
    @SerializedName("rates") val rates: Map<String, Double>
)
