package com.example.retobcpcurrency.data.network.response

data class CurrencyResponse(
    val base: String,
    val error: String,
    val results: Results
)