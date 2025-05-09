package com.jaya.currency_converter_api.dto

data class CurrencyConverterErrorDTO(
    val error: ErrorDTO
)

data class ErrorDTO(
    val code: String,
    val message: String
)