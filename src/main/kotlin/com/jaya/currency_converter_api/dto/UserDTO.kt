package com.jaya.currency_converter_api.dto

import com.jaya.currency_converter_api.entity.enums.CurrencyConverterProviderEnum
import com.jaya.currency_converter_api.entity.model.User
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "User")
data class UserDTO(
    @field:Schema(
        description = "UUID",
        example = "76d12679-5c5d-4490-933e-32a18bca5ba4",
        type = "String",
        minimum = "36",
        maximum = "36"
    )
    val id: UUID?,
    @field:Schema(
        description = "Name of the user",
        example = "Berg Cavalcante",
        type = "String"
    )
    val name: String,
    @field:Schema(
        description = "Email of the user (optional)",
        example = "Berg Cavalcante",
        type = "String"
    )
    val email: String?,
    @field:Schema(
        description = "Key for Configuration for external api provider: [EXCHANGE_RATES_API_PROVIDER, SANDBOX_PROVIDER, NONE]",
        example = "EXCHANGE_RATES_API_PROVIDER"
    )
    val provider: CurrencyConverterProviderEnum,
) {
    constructor(user: User) : this(user.id, user.name, user.email, user.provider)
}