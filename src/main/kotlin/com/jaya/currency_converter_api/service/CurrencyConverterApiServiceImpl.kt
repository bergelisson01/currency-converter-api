package com.jaya.currency_converter_api.service

import BadRequestException
import NotFoundException
import OperationException
import com.jaya.currency_converter_api.configuration.ConfigApiProviderProperties
import com.jaya.currency_converter_api.dto.*
import com.jaya.currency_converter_api.entity.enums.CurrencyConverterStatusEnum
import com.jaya.currency_converter_api.entity.model.Transaction
import com.jaya.currency_converter_api.repository.UserRepository
import com.jaya.currency_converter_api.strategy.CurrencyConverterStrategy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.UUID

@Service
class CurrencyConverterApiServiceImpl(
    val configApiProviderProperties: ConfigApiProviderProperties,
    val userRepository: UserRepository,
    val clientApiService: ClientApiServiceImpl
) : CurrencyConverterApiService {

    override fun convert(userId: UUID, request: CurrencyConverterDTO): CurrencyResponseDTO<CurrencyConverterResponseDTO> {
        validateRequest(request)
        val opt = this.userRepository.findById(userId)
        if (opt.isEmpty) throw NotFoundException("Not found user for id ${userId}.")
        val user = opt.get()
        val currencyApiService = CurrencyConverterStrategy
            .resolver(user, this.configApiProviderProperties, this.clientApiService)
            ?: throw BadRequestException("Unsupported api service for provider ${user.provider}.")

        return currencyApiService.convert(request)
    }

    override fun getRates(userId: UUID, base: String): CurrencyResponseDTO<CurrencyRatesDTO> {
        if (base.isNullOrBlank()) throw OperationException("Base currency should not be null or empty")
        val opt = this.userRepository.findById(userId)
        if (opt.isEmpty) throw NotFoundException("Not found user for id ${userId}.")
        val user = opt.get()
        val currencyApiService = CurrencyConverterStrategy
            .resolver(user, this.configApiProviderProperties, this.clientApiService)
            ?: throw BadRequestException("Unsupported api service for provider ${user.provider}.")

        val response = currencyApiService.getRates(base)
        return CurrencyResponseDTO(response, null, HttpStatus.OK.value())
    }

    private fun validateRequest(request: CurrencyConverterDTO) {
        if (request == null) throw OperationException("request should not be null.")
        if (request.to.isNullOrBlank()) throw OperationException("request[to] should not be null or empty.")
        if (request.from.isNullOrBlank()) throw OperationException("request[from] should not be null or empty.")
        if (request.amount == null || request.amount <= 0) throw OperationException("request[amount] should not be null or lower than zero.")
    }
}