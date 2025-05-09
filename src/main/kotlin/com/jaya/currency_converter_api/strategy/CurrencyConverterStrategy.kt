package com.jaya.currency_converter_api.strategy

import OperationException
import com.jaya.currency_converter_api.configuration.ConfigApiProviderProperties
import com.jaya.currency_converter_api.entity.enums.CurrencyConverterProviderEnum
import com.jaya.currency_converter_api.entity.model.User
import com.jaya.currency_converter_api.service.ClientApiService
import com.jaya.currency_converter_api.service.internal.CurrencyApiService
import com.jaya.currency_converter_api.service.internal.ExchangeRatesApiServiceImpl
import com.jaya.currency_converter_api.service.internal.SandboxCurrencyApiServiceImpl

class CurrencyConverterStrategy {
    companion object {
        fun resolver(
            user: User,
            configApiProviderProperties: ConfigApiProviderProperties,
            clientApiService: ClientApiService
        ): CurrencyApiService? {
            val config = configApiProviderProperties.providers.find { it.key.equals(user.provider) }
                ?: throw OperationException("Provider Config not found for ${user.provider}.")

            return when (user.provider) {
                CurrencyConverterProviderEnum.SANDBOX_PROVIDER -> SandboxCurrencyApiServiceImpl(config, clientApiService)
                CurrencyConverterProviderEnum.EXCHANGE_RATES_API_PROVIDER -> ExchangeRatesApiServiceImpl(user, config, clientApiService)
                else -> null
            }
        }
    }
}