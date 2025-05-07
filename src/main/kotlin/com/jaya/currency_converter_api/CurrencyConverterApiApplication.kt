package com.jaya.currency_converter_api

import com.jaya.currency_converter_api.configuration.ConfigApiProviderProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ConfigApiProviderProperties::class)
class CurrencyConverterApiApplication

fun main(args: Array<String>) {
	runApplication<CurrencyConverterApiApplication>(*args)
}
