package com.example.currencyapp.data

data class CurrencyExchangeRate(
    val tarihDate: String,
    val currencyTitle: String,
    val unit: Int,
    val isim: String,
    val currencyName: String,
    val banknoteBuying: Double,
    val banknoteSelling: Double,
    val forexBuying: Double,
    val forexSelling: Double,
    val currencyCode: String,
)