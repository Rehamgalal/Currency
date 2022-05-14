package com.scan.base.data

import com.scan.base.utils.AppGeneralEventsBus
import com.scan.base.utils.NetworkConnectivityHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

open class BaseRepo(
    private val networkConnectivityHelper: NetworkConnectivityHelper,
    private val appGeneralEventsBus: AppGeneralEventsBus
) {

    protected fun <T> networkOnlyFlow(remoteCall: suspend () -> T): Flow<Resource<T>> {
        suspend fun fetchFromNetwork(): T {
            if (networkConnectivityHelper.isConnected()) return remoteCall()
            else throw NoInternetException()
        }

        return flow<Resource<T>> {
            emit(Resource.Loading)

            try {
                emit(Resource.Success(fetchFromNetwork()))
            } catch (e: Exception) {
                handleApiException(e, this)
            }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun <T> handleApiException(
        e: Exception,
        flowCollector: FlowCollector<Resource<T>>
    ) {
        e.printStackTrace()
        if (e is UnauthorizedApiException)
            appGeneralEventsBus.post(AppGeneralEventsBus.AppEvent.InvalidateUser)
        else
            flowCollector.emit(Resource.Error(e))
    }



}
