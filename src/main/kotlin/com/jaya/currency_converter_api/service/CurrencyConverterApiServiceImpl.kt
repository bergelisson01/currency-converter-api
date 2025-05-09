package com.jaya.currency_converter_api.service

import BadRequestException
import NotFoundException
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
class CurrencyConverterApiServiceImpl : CurrencyConverterApiService {

    @Autowired
    lateinit var configApiProviderProperties: ConfigApiProviderProperties
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var clientApiService: ClientApiServiceImpl

    override fun convert(userId: UUID, request: CurrencyConverterDTO): CurrencyResponseDTO<ConvertResponse> {
        val opt = this.userRepository.findById(userId)
        if (opt.isEmpty) throw NotFoundException("Not found user for id ${userId}.")
        val user = opt.get()
        val currencyApiService = CurrencyConverterStrategy
            .resolver(user, this.configApiProviderProperties, this.clientApiService)
            ?: throw BadRequestException("Unsupported api service for provider ${user.provider}.")

        return currencyApiService.convert(request)
    }

    override fun getRates(userId: UUID, base: String): CurrencyResponseDTO<CurrencyRatesDTO> {
        val opt = this.userRepository.findById(userId)
        if (opt.isEmpty) throw NotFoundException("Not found user for id ${userId}.")
        val user = opt.get()
        val currencyApiService = CurrencyConverterStrategy
            .resolver(user, this.configApiProviderProperties, this.clientApiService)
            ?: throw BadRequestException("Unsupported api service for provider ${user.provider}.")

        val response = currencyApiService.getRates(base)
        return CurrencyResponseDTO(response, null, HttpStatus.OK.value())
    }
}