package com.jaya.currency_converter_api.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "CurrencyResponse")
data class CurrencyResponseDTO<T>(
    @field:Schema(
        anyOf = [CurrencyConverterResponseDTO::class, UserDTO::class, TransactionDTO::class],
        description = "Data has the return entity."
    )
    val data: T?,
    @field:Schema(
        oneOf = [CurrencyConverterErrorDTO::class],
        description = "Error object",
    )
    val error: Any?,
    @field:Schema(
        description = "Http Status Code",
        example = "[200, 400, 404, 500...]"
    )
    val statusCode: Int
)

@Schema(name = "CurrencyResponseCurrencyConverter")
data class CurrencyResponseCurrencyConverter(
    @field:Schema(
        description = "Data has the return entity."
    )
    val data: CurrencyConverterResponseDTO?,
    @field:Schema(
        oneOf = [CurrencyConverterErrorDTO::class],
        description = "Error object",
    )
    val error: CurrencyConverterErrorDTO?,
    @field:Schema(
        description = "Http Status Code",
        example = "[200, 400, 404, 500...]"
    )
    val statusCode: Int
)

@Schema(name = "CurrencyResponseRates")
data class CurrencyResponseRates(
    @field:Schema(
        description = "Data has the return entity."
    )
    val data: CurrencyRatesDTO?,
    @field:Schema(
        oneOf = [CurrencyConverterErrorDTO::class],
        description = "Error object",
    )
    val error: CurrencyConverterErrorDTO?,
    @field:Schema(
        description = "Http Status Code",
        example = "[200, 400, 404, 500...]"
    )
    val statusCode: Int
)

@Schema(name = "CurrencyResponseUser")
data class CurrencyResponseUser(
    @field:Schema(
        description = "Data has the return entity."
    )
    val data: UserDTO?,
    @field:Schema(
        oneOf = [CurrencyConverterErrorDTO::class],
        description = "Error object",
    )
    val error: CurrencyConverterErrorDTO?,
    @field:Schema(
        description = "Http Status Code",
        example = "[200, 400, 404, 500...]"
    )
    val statusCode: Int
)

@Schema(name = "CurrencyResponseListUser")
data class CurrencyResponseListUser(
    @field:Schema(
        description = "Data has the return entity."
    )
    val data: List<UserDTO>?,
    @field:Schema(
        oneOf = [CurrencyConverterErrorDTO::class],
        description = "Error object",
    )
    val error: CurrencyConverterErrorDTO?,
    @field:Schema(
        description = "Http Status Code",
        example = "[200, 400, 404, 500...]"
    )
    val statusCode: Int
)

@Schema(name = "CurrencyResponseTransaction")
data class CurrencyResponseTransaction(
    @field:Schema(
        description = "Data has the return entity."
    )
    val data: TransactionDTO?,
    @field:Schema(
        oneOf = [CurrencyConverterErrorDTO::class],
        description = "Error object",
    )
    val error: CurrencyConverterErrorDTO?,
    @field:Schema(
        description = "Http Status Code",
        example = "[200, 400, 404, 500...]"
    )
    val statusCode: Int
)

@Schema(name = "CurrencyResponseListTransaction")
data class CurrencyResponseListTransaction(
    @field:Schema(
        description = "Data has the return entity."
    )
    val data: List<TransactionDTO>?,
    @field:Schema(
        oneOf = [CurrencyConverterErrorDTO::class],
        description = "Error object",
    )
    val error: CurrencyConverterErrorDTO?,
    @field:Schema(
        description = "Http Status Code",
        example = "[200, 400, 404, 500...]"
    )
    val statusCode: Int
)