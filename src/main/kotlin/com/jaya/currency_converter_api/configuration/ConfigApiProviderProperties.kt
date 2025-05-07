package com.jaya.currency_converter_api.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "config.api")
data class ConfigApiProviderProperties (
    val providers: List<ConfigApiProvider>
)