package com.jaya.currency_converter_api.dto

import com.jaya.currency_converter_api.entity.enums.CurrencyConverterProviderEnum
import com.jaya.currency_converter_api.entity.model.User
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
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
    @field:NotNull(message = "Name must not be null")
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
    @field:NotNull(message = "provider must not be null")
    val provider: CurrencyConverterProviderEnum,
    @field:Schema(
        description = "Access Key for external provider api",
        example = "1290823918018237987123810",
        type = "String"
    )
    val accessKey: String?,
) {
    constructor(user: User) : this(user.id, user.name, user.email, user.provider, user.accessKey)
}