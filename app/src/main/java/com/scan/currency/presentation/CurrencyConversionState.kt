package com.scan.currency.presentation

import com.scan.cureencybase.view.UiState
import com.scan.currencydata.network.models.response.CurrencyConversionResponse

data class CurrencyConversionState(    val loading: Boolean = false,
                                       val failed: Boolean = false,
                                       val success: Boolean = false,
val currencyResponse: CurrencyConversionResponse?=null): UiState {

    companion object {
        fun loading() = CurrencyConversionState(loading = true)
        fun failed() = CurrencyConversionState(failed = true)
        fun success() = CurrencyConversionState(success = true)
    }
                                     }