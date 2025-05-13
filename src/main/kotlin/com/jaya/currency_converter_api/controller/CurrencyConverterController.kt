package com.jaya.currency_converter_api.controller

import ApiIntegrationException
import BadRequestException
import NotFoundException
import OperationException
import com.jaya.currency_converter_api.dto.*
import com.jaya.currency_converter_api.entity.model.Transaction
import com.jaya.currency_converter_api.service.CurrencyConverterApiServiceImpl
import com.jaya.currency_converter_api.service.TransactionServiceImpl
import com.jaya.currency_converter_api.service.internal.ExchangeRatesApiServiceImpl
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "Currency", description = "Currency API")
@RestController
@RequestMapping("/api/currency")
class CurrencyConverterController(
    private val currencyConverterService: CurrencyConverterApiServiceImpl,
    private val transactionService: TransactionServiceImpl
) {
    private val logger = KotlinLogging.logger(CurrencyConverterController::class.toString())

    @PostMapping("/{userId}/convert")
    @Operation(
        summary = "Converts a given amount from EUR to another valid currency using the latest exchange rates.",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = """
## Field Descriptions
### from (String): The base currency. Only EUR is allowed. Any other value should result in a 400 Bad Request.
### to (String): The target currency to which the conversion will be made. Must be a valid ISO currency code supported by the external exchange rate API. If the currency is invalid or unsupported, return a 400 Bad Request.
### amount (Number / Double): The numeric value to convert. Must be greater than zero. If the value is zero or negative, return a 400 Bad Request.
## Validation Rules
### from !== "EUR" → 400 Bad Request with an error message.
### to not in supported currencies → 400 Bad Request with an error message.
### amount <= 0 → 400 Bad Request with an error message.

## A successful or error operation should create a transaction.
            """,
            required = true,
            content = [Content(schema = Schema(implementation = CurrencyConverterDTO::class))]
        ))
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = """
## Response
### data (Object): The base currency. Only EUR is allowed. Any other value should result in a 400 Bad Request.
### to (String): The target currency to which the conversion will be made. Must be a valid ISO currency code supported by the external exchange rate API. If the currency is invalid or unsupported, return a 400 Bad Request.
### amount (Number / Double): The numeric value to convert. Must be greater than zero. If the value is zero or negative, return a 400 Bad Request.
# Validation Rules
### from !== "EUR" → 400 Bad Request with an error message.
### to not in supported currencies → 400 Bad Request with an error message.
### amount <= 0 → 400 Bad Request with an error message.
            """,
            content = [
                Content(
                    schema = Schema(implementation = CurrencyResponseDTO::class)
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
    fun postConvert(
        @PathVariable userId: UUID,
        @Valid @RequestBody request: CurrencyConverterDTO
    ): ResponseEntity<Any> {
        var response: CurrencyResponseDTO<CurrencyConverterResponseDTO>
        try {
            response = currencyConverterService.convert(userId, request)
        } catch (e: ApiIntegrationException) {
            this.transactionService.registerTransactionFromConverterRequest(userId, request, null, e.message)
            this.logger.error { "Error reading response body: ${e.message}" }
            return ResponseEntity.status(HttpStatus.valueOf(e.statusCode)).body(e.message)
        } catch (e: Exception) {
            this.transactionService.registerTransactionFromConverterRequest(userId, request, null, e.message)
            val error = e.message ?: "General error"
            val code = when(e) {
                is OperationException -> "operation_error"
                is NotFoundException -> "not_found_error"
                is BadRequestException -> "bar_redquest_error"
                else -> "general_error"
            }
            val body = CurrencyConverterErrorDTO(ErrorDTO(code, error))
            this.logger.error { "Error reading response body: ${body}" }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
        }

        response.data?.let {
            this.transactionService.registerTransactionFromConverterRequest(userId, request, it, null)
            this.logger.info { "Success: ${it}" }
            return ResponseEntity.ok(it)
        }

        this.transactionService.registerTransactionFromConverterRequest(userId, request, null, response.error)
        this.logger.error { "Error reading response body: ${response.error}" }
        return ResponseEntity.status(response.statusCode).body(response.error)
    }

    @GetMapping("/{userId}/rates")
    @Operation(summary = "Get rates from external api")
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = """
## Field Descriptions
### userId (UUID) (path param): The UUID of an existent user in database.
### base (String) (query param): The base currency to get the rates from. IF the currency is invalid or unsupported, return a 400 Bad Request.
## Validation Rules
### from !== "EUR" → 400 Bad Request with an error message.
            """,
            content = [
                Content(
                    schema = Schema(implementation = CurrencyResponseDTO::class)
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
    fun getRates(
        @PathVariable userId: UUID,
        @RequestParam("base") base: String
    ): ResponseEntity<Any> {
        var response: CurrencyResponseDTO<CurrencyRatesDTO>
        try {
            response = currencyConverterService.getRates(userId, base)
        } catch (e: ApiIntegrationException) {
            this.logger.error { "Error reading response body: ${e.message}" }
            return ResponseEntity.status(HttpStatus.valueOf(e.statusCode)).body(e.message)
        } catch (e: Exception) {
            val error = e.message ?: "General error"
            val code = when(e) {
                is OperationException -> "operation_error"
                is NotFoundException -> "not_found_error"
                is BadRequestException -> "bar_redquest_error"
                else -> "general_error"
            }
            val body = CurrencyConverterErrorDTO(ErrorDTO(code, error))
            this.logger.error { "Error reading response body: ${body}" }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
        }

        response.data?.let {
            this.logger.info { "Success: ${it}" }
            return ResponseEntity.ok(it)
        }

        this.logger.error { "Error reading response body: ${response.error}" }
        return ResponseEntity.status(response.statusCode).body(response.error)
    }
}