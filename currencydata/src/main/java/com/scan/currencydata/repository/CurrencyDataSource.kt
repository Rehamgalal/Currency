package com.scan.currencydata.repository

import com.scan.cureencybase.data.BaseRemoteDataSource
import com.scan.currencydata.network.api.Api
import com.scan.currencydata.network.models.response.CurrencyConversionResponse
import com.scan.currencydata.repository.RemoteDataSource

class CurrencyDataSource(val api: Api): RemoteDataSource, BaseRemoteDataSource() {
    override suspend fun convertAmount(
        currentCurrency: String,
        amount: Double,
        desiredCurrency: String
    ): CurrencyConversionResponse {
        return makeRequest {   api.convert(currentCurrency,desiredCurrency,amount) }
    }
}