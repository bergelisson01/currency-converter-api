package com.jaya.currency_converter_api.controller

import com.jaya.currency_converter_api.configuration.ConfigApiProvider
import com.jaya.currency_converter_api.configuration.ConfigApiProviderProperties
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/configuration")
class ConfigrationProviderController(
    private val configApiProviderProperties: ConfigApiProviderProperties
) {

    @GetMapping("/providers")
    fun getItems(): List<ConfigApiProvider> = configApiProviderProperties.providers
}