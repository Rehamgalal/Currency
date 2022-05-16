package com.scan.currencydata.repository

import com.scan.cureencybase.data.BaseRepo
import com.scan.cureencybase.data.Resource
import com.scan.cureencybase.utils.AppGeneralEventsBus
import com.scan.cureencybase.utils.NetworkConnectivityHelper
import com.scan.currencydata.network.models.response.CurrencyConversionResponse
import com.scan.currencydata.repository.CurrencyRepo
import com.scan.currencydata.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow

class CurrencyRepoImplementation(networkConnectivityHelper: NetworkConnectivityHelper,
                                 appGeneralEventsBus: AppGeneralEventsBus,
                                 private val remoteDataSource: RemoteDataSource
): CurrencyRepo, BaseRepo(networkConnectivityHelper,appGeneralEventsBus) {
    override fun convertAmount(
        currentCurrency: String,
        amount: Double,
        desiredCurrency: String
    ): Flow<Resource<CurrencyConversionResponse>> {
        return networkOnlyFlow { remoteDataSource.convertAmount(currentCurrency,amount,desiredCurrency) }
    }
}