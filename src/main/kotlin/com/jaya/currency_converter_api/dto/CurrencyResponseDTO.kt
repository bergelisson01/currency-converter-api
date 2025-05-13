package com.jaya.currency_converter_api.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "CurrencyResponse")
data class CurrencyResponseDTO<T>(
    @field:Schema(
        description = "Data has the return entity."
    )
    val data: T?,
    @field:Schema(
        description = "Error message",
        example = "Provided API key is invalid.",
        type = "String"
    )
    val error: String?,
    @field:Schema(
        description = "Http Status Code",
        example = "[200, 400, 404, 500...]"
    )
    val statusCode: Int
)