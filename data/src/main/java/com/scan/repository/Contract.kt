package com.scan.repository

import com.scan.base.data.Resource
import com.scan.network.models.response.CurrencyConversionResponse
import kotlinx.coroutines.flow.Flow

interface CurrencyRepo {
    fun convertAmount(currentCurrency: String, amount: Double, desiredCurrency: String) : Flow<Resource<Int>>
}

interface RemoteDataSource {
    suspend fun convertAmount(currentCurrency: String, amount: Double, desiredCurrency: String) : CurrencyConversionResponse
}