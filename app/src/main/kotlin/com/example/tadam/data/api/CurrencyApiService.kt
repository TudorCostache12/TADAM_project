package com.example.tadam.data.api

import com.example.tadam.data.model.RatesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("latest")
    suspend fun getLatest(@Query("base") base: String = "EUR"): RatesResponse
}
