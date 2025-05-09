package com.jaya.currency_converter_api.service.internal

import com.jaya.currency_converter_api.dto.CurrencyResponseDTO
import com.jaya.currency_converter_api.dto.CurrencyConverterDTO
import com.jaya.currency_converter_api.dto.ConvertResponse
import com.jaya.currency_converter_api.dto.CurrencyRatesDTO

interface CurrencyApiService {
    fun convert(request: CurrencyConverterDTO): CurrencyResponseDTO<ConvertResponse>
    fun getRates(baseCurrency: String): CurrencyRatesDTO
}