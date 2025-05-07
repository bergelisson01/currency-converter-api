package com.jaya.currency_converter_api.configuration

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(Info().title("Jaya Challange - Currency Converter Api")
                .description("Currency Converter API A RESTful API for currency conversion, designed to handle real-time exchange rates and log every transaction performed by users. Built as part of a technical challenge, this project demonstrates clean architecture, data persistence, and integration with external APIs using modern Kotlin and MySQL.")
                .version("v1.0"))
    }
}