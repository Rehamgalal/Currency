package com.scan.repository

import com.scan.base.data.BaseRepo
import com.scan.base.data.Resource
import com.scan.base.utils.AppGeneralEventsBus
import com.scan.base.utils.NetworkConnectivityHelper
import kotlinx.coroutines.flow.Flow

class CurrencyRepoImplementation(networkConnectivityHelper: NetworkConnectivityHelper,
                                 appGeneralEventsBus: AppGeneralEventsBus,
                                 private val remoteDataSource: RemoteDataSource
): CurrencyRepo, BaseRepo(networkConnectivityHelper,appGeneralEventsBus) {
    override fun convertAmount(
        currentCurrency: String,
        amount: Double,
        desiredCurrency: String
    ): Flow<Resource<Int>> {
        return networkOnlyFlow { remoteDataSource.convertAmount(currentCurrency,amount,desiredCurrency) }
    }
}