package com.jaya.currency_converter_api.service

import com.jaya.currency_converter_api.dto.CurrencyResponseDTO
import org.springframework.core.ParameterizedTypeReference
import org.springframework.util.MultiValueMap

interface ClientApiService {
    fun <T> get(path: String, params: MultiValueMap<String, String>?, clazz: ParameterizedTypeReference<T>): CurrencyResponseDTO<T>
}