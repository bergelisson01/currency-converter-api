package com.jaya.currency_converter_api.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class CurrencyConverterDTO(
    @field:Schema(
        description = "Base currency",
        example = "EUR",
        type = "String"
    )
    @field:NotNull(message = "Base currency must not be null")
    val from: String,
    @field:Schema(
        description = "Destination currency",
        example = "BRL",
        type = "String"
    )
    @field:NotNull(message = "Destination currency must not be null")
    val to: String,
    @field:Schema(
        description = "Amount",
        example = "25",
        type = "String"
    )
    @field:NotNull(message = "amount must not be null")
    @field:Min(value = 1, message = "Value must be grater than 0")
    val amount: Double
)