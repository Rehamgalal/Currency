package com.scan.repository

import com.scan.base.data.BaseRemoteDataSource
import com.scan.network.api.Api
import com.scan.network.models.response.CurrencyConversionResponse

class CurrencyDataSource(val api: Api): RemoteDataSource, BaseRemoteDataSource() {
    override suspend fun convertAmount(
        currentCurrency: String,
        amount: Double,
        desiredCurrency: String
    ): CurrencyConversionResponse {
        return makeRequest {   api.convert(currentCurrency,desiredCurrency,amount) }
    }
}