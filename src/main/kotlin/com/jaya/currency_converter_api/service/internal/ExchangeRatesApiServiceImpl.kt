package com.jaya.currency_converter_api.service.internal

import BadRequestException
import OperationException
import com.jaya.currency_converter_api.configuration.ConfigApiProvider
import com.jaya.currency_converter_api.dto.*
import com.jaya.currency_converter_api.entity.model.User
import com.jaya.currency_converter_api.service.ClientApiService
import com.jaya.currency_converter_api.utils.CurrencyUtils
import mu.KotlinLogging
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.util.*

class ExchangeRatesApiServiceImpl : CurrencyApiService {
    private val ALLOWED_BASE_CURRENCY = listOf("EUR")

    private val logger = KotlinLogging.logger(ExchangeRatesApiServiceImpl::class.toString())

    private val user: User
    private val providerConfig: ConfigApiProvider
    private val clientApiService: ClientApiService

    constructor(user: User, config: ConfigApiProvider, clientApiService: ClientApiService) {
        this.user = user
        this.providerConfig = config
        this.clientApiService = clientApiService
    }

    override fun convert(request: CurrencyConverterDTO): CurrencyResponseDTO<ConvertResponse> {
        if (request.from.isEmpty() || !ALLOWED_BASE_CURRENCY.contains(request.from)) {
            throw OperationException("Not supported base currency ${request.from}. Please Try ${ALLOWED_BASE_CURRENCY}.")
        }

        val ratesResponse = this.getRates(request.from)

        if (!ratesResponse.success) {
            throw BadRequestException("Rates not found for current request.")
        }

        val rate = ratesResponse.rates?.get(request.to) ?: throw BadRequestException("Rate not found for currency ${request.to}.")

        val query = Query(request.from, request.to, request.amount)
        val info = Info(Date().time, rate)
        val result = CurrencyUtils.roundDouble(6, rate * request.amount)
        val date = CurrencyUtils.formatDate("dd-MM-yyyy", Date())
        val convertedResponse = ConvertResponse(true, query, info, "", date, result)
        this.logger.info { "Successfuly converted: ${request} to ${convertedResponse}" }
        return CurrencyResponseDTO(convertedResponse, null, HttpStatus.OK.value())
    }

    override fun getRates(baseCurrency: String): CurrencyRatesDTO {
        if (baseCurrency.isEmpty() || !ALLOWED_BASE_CURRENCY.contains(baseCurrency)) {
            throw OperationException("Not supported base currency ${baseCurrency}. Please Try ${ALLOWED_BASE_CURRENCY}.")
        }

        val response = this.clientApiService.get(
            providerConfig.url,
            this.buildParams(),
            object : ParameterizedTypeReference<CurrencyRatesDTO>() {}
        )

        if (response.statusCode != HttpStatus.OK.value()) {
            throw BadRequestException(response.error ?: "Rates not found for current request.")
        }

        response.data?.let {
            this.logger.info { "Get Rates Successfuly executed: ${it}" }
        }

        return response.data ?: throw BadRequestException("Rates not found for current request.")
    }

    private fun buildParams(): MultiValueMap<String, String> {
        val tempMap: MutableMap<String, MutableList<String>> = mutableMapOf()
        tempMap["access_key"] = mutableListOf(this.user.accessKey ?: providerConfig.apiKey)
        return LinkedMultiValueMap(tempMap)
    }
}