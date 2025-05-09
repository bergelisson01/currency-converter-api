package com.jaya.currency_converter_api.configuration

import com.jaya.currency_converter_api.entity.enums.CurrencyConverterProviderEnum
import com.jaya.currency_converter_api.entity.model.User
import com.jaya.currency_converter_api.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer(private val userRepository: UserRepository) : CommandLineRunner {

    override fun run(vararg args: String) {
//        userRepository.deleteAll()
//        userRepository.save(User())
//        userRepository.save(User("Exchange", CurrencyConverterProviderEnum.EXCHANGE_RATES_API_PROVIDER, "212c807c558aaa2a2dba5a98696d08e5"))
    }
}