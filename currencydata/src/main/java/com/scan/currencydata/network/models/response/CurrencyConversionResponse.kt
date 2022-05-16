package com.scan.currencydata.network.models.response

data class CurrencyConversionResponse(val success:Boolean, val query: Query, val info: Info, val date: String, val result: Double) {
    data class Query(val from:String,val to:String,val amount: Int)
    data class Info(val timestamp:Long,val rate: Double)
}