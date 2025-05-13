package com.jaya.currency_converter_api.dto

import com.jaya.currency_converter_api.entity.enums.CurrencyConverterProviderEnum
import com.jaya.currency_converter_api.entity.enums.CurrencyConverterStatusEnum
import com.jaya.currency_converter_api.entity.model.Transaction
import com.jaya.currency_converter_api.utils.CurrencyUtils
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import java.time.LocalDate
import java.util.*
@Schema(description = "Usetransaction")
data class TransactionDTO(
    @field:Schema(
        description = "UUID",
        example = "76d12679-5c5d-4490-933e-32a18bca5ba4",
        type = "String",
        minimum = "36",
        maximum = "36"
    )
    val uid: UUID?,
    @field:Schema(
        description = "User entity.",
    )
    val user: UserDTO?,
    @field:Schema(
        description = "Base currency",
        example = "EUR",
        type = "String"
    )
    var originCurrency: String,
    @field:Schema(
        description = "Base value to be converted",
        example = "25",
        type = "Double"
    )
    var originValue: Double,
    @field:Schema(
        description = "Destination currency",
        example = "BRL",
        type = "String"
    )
    var destinationCurrency: String,
    @field:Schema(
        description = "Converted value",
        example = "13.567456",
        type = "Double"
    )
    var convertedValue: Double?,
    @field:Schema(
        description = "Conversion rate.",
        example = "0.765898",
        type = "Double"
    )
    var conversionRate: Double?,
    @field:Schema(
        description = "Request date in format dd-MM-yyyyTHH:mm:ss:SSS",
        example = "13-05-2025 08:44:13.054",
        type = "String"
    )
    val date: String,
    @field:Schema(
        description = "Currency Converter Provider",
        example = "EXCHANGE_RATES_API_PROVIDER",
        type = "String"
    )
    var provider: CurrencyConverterProviderEnum,
    @field:Schema(
        description = "Transaction status",
        example = "SUCCESS",
        type = "String"
    )
    var status: CurrencyConverterStatusEnum?,
    @field:Schema(
        description = "Error",
        example = "can by only a msg or an jason object response for external api.",
        type = "String"
    )
    var error: String?
) {
    constructor(transaction: Transaction) : this(
        transaction.uid,
        if (transaction.user == null) null else transaction.user?.let { UserDTO(it) },
        transaction.originCurrency,
        transaction.originValue,
        transaction.destinationCurrency,
        transaction.convertedValue,
        transaction.conversionRate,
        CurrencyUtils.formatDate("dd-MM-yyyy HH:mm:ss.SSS", transaction.date),
        transaction.provider,
        transaction.status,
        transaction.error
    )
}