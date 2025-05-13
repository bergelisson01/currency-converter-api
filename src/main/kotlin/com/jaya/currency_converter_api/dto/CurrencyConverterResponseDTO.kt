package com.jaya.currency_converter_api.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "CurrencyConverterResponse")
data class CurrencyConverterResponseDTO(
    @field:Schema(
        description = "true/false for success/fail request."
    )
    val success: Boolean,
    @field:Schema(
        description = "Query with the request info."
    )
    val query: Query,
    @field:Schema(
        description = "Info with the data used for calculation.",
     )
    val info: Info,
    @field:Schema(
        description = "Request date in format dd-MM-yyyyTHH:mm:ss:SSS",
        example = "13-05-2025 08:44:13.054",
        type = "String"
    )
    val date: String,
    @field:Schema(
        description = "Result with 6 decimal places",
        example = "13.564738",
        type = "Double"
    )
    val result: Double
)

@Schema(name = "Query")
data class Query(
    @field:Schema(
        description = "Base currency",
        example = "EUR",
        type = "String"
    )
    val from: String,
    @field:Schema(
        description = "Destination currency",
        example = "BRL",
        type = "String"
    )
    val to: String,
    @field:Schema(
        description = "Value to be converted",
        example = "25",
        type = "Double"
    )
    val amount: Double
)

@Schema(name = "Info")
data class Info(
    @field:Schema(
        description = "Timestamp of the execution.",
        example = "1747136653053",
        type = "Long"
    )
    val timestamp: Long,
    @field:Schema(
        description = "Rate applied in query.amount.",
        example = "1.564738",
        type = "Double"
    )
    val rate: Double
)