package com.jaya.currency_converter_api.controller

import com.jaya.currency_converter_api.configuration.ConfigApiProvider
import com.jaya.currency_converter_api.configuration.ConfigApiProviderProperties
import com.jaya.currency_converter_api.entity.model.Transaction
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Providers", description = "Providers API")
@RestController
@RequestMapping("/api/configuration")
class ConfigrationProviderController(
    private val configApiProviderProperties: ConfigApiProviderProperties
) {

    @GetMapping("/providers")
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = [
                Content(
                    mediaType = "application/json",
                    array = ArraySchema(schema = Schema(implementation = ConfigApiProvider::class))
                )
            ]),
        ApiResponse(
            responseCode = "404",
            description = "User Not Found"
        ),
        ApiResponse(
            responseCode = "400",
            description = "Bad Request"
        ),
        ApiResponse(
            responseCode = "500",
            description = "Internal Server Error"
        )
    ])
    fun getItems(): List<ConfigApiProvider> = configApiProviderProperties.providers
}