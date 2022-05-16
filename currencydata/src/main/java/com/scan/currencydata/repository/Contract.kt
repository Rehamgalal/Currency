package com.scan.currencydata.repository

import com.scan.cureencybase.data.Resource
import com.scan.currencydata.network.models.response.CurrencyConversionResponse
import kotlinx.coroutines.flow.Flow

interface CurrencyRepo {
    fun convertAmount(currentCurrency: String, amount: Double, desiredCurrency: String) : Flow<Resource<CurrencyConversionResponse>>
}

interface RemoteDataSource {
    suspend fun convertAmount(currentCurrency: String, amount: Double, desiredCurrency: String) : CurrencyConversionResponse
}