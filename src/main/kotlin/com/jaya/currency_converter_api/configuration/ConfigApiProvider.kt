package com.jaya.currency_converter_api.configuration

import com.jaya.currency_converter_api.entity.enums.CurrencyConverterProviderEnum

data class ConfigApiProvider(
    val name: String,
    val key: CurrencyConverterProviderEnum,
    val url: String,
    val apiKey: String
)