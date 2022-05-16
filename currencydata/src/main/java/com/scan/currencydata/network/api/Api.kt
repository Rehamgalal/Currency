package com.scan.currencydata.network.api

import com.scan.currencydata.network.models.response.CurrencyConversionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

@GET("convert")
suspend fun convert(@Query("from")convertFrom: String,@Query("to")convertTo:String,@Query("amount")amount: Double): Response<CurrencyConversionResponse>
}