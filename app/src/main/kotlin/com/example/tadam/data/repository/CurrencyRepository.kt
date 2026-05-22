package com.example.tadam.data.repository

import com.example.tadam.data.api.RetrofitProvider
import com.example.tadam.data.model.RatesResponse

class CurrencyRepository {
    private val api = RetrofitProvider.api

    suspend fun getLatestRates(base: String): RatesResponse {
        return api.getLatest(base)
    }
}
