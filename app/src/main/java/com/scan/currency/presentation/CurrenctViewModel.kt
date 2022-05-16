package com.scan.currency.presentation

import androidx.lifecycle.viewModelScope
import com.scan.cureencybase.data.Resource
import com.scan.cureencybase.utils.AppGeneralEventsBus
import com.scan.cureencybase.view.BaseViewModel
import com.scan.currencydata.repository.CurrencyRepo
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CurrenctViewModel(appGeneralEventsBus: AppGeneralEventsBus,
                        private val repository: CurrencyRepo
): BaseViewModel<CurrencyConversionState>(appGeneralEventsBus,CurrencyConversionState.loading()) {

    fun convert(currentCurrency: String, amount: Double, desiredCurrency: String) {
        repository.convertAmount(currentCurrency,amount,desiredCurrency).onEach {
            when (it) {
                is Resource.Success -> {

                    updateState(currentState().copy(
                        loading = false,
                        failed = false,
                        success = true,
                        currencyResponse = it.data
                    ))
                }
                is Resource.Loading -> updateState(CurrencyConversionState.loading())

                is Resource.Error -> {
                    updateState(CurrencyConversionState.failed())
                    sendErrorMsgSingleEvent(it.exception.message)
                }
            }

        }.launchIn(viewModelScope)
    }
}