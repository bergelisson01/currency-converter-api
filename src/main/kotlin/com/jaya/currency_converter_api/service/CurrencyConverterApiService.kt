package com.jaya.currency_converter_api.service

import com.jaya.currency_converter_api.dto.CurrencyResponseDTO
import com.jaya.currency_converter_api.dto.CurrencyConverterDTO
import com.jaya.currency_converter_api.dto.ConvertResponse
import com.jaya.currency_converter_api.dto.CurrencyRatesDTO
import java.util.*

interface CurrencyConverterApiService {
    fun convert(userId: UUID, request: CurrencyConverterDTO): CurrencyResponseDTO<ConvertResponse>
    fun getRates(userId: UUID, base: String): CurrencyResponseDTO<CurrencyRatesDTO>
}