package com.jaya.currency_converter_api.service

import com.jaya.currency_converter_api.dto.*
import java.util.*

interface CurrencyConverterApiService {
    fun convert(userId: UUID, request: CurrencyConverterDTO): CurrencyResponseDTO<CurrencyConverterResponseDTO>
    fun getRates(userId: UUID, base: String): CurrencyResponseDTO<CurrencyRatesDTO>
}