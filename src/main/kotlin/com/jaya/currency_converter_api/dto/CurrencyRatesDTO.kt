package com.jaya.currency_converter_api.dto

data class CurrencyRatesDTO(
    val success: Boolean,
    val timestamp: Long?,
    val base: String?,
    val date: String?,
    val rates: Map<String, Double>?,
    val error: CurrencyError?
)

data class CurrencyError(
    val code: Int,
    val info: String
)