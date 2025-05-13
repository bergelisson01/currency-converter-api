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
import org.springframework.validation.annotation.Validated
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
@Validated
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
<h2>Field Descriptions</h2>

<ul>
  <li>
    <strong>from</strong> (<code>String</code>):<br>
    The base currency. Only <code>EUR</code> is allowed.<br>
    Any other value should result in a <code>400 Bad Request</code>.
  </li>

  <li>
    <strong>to</strong> (<code>String</code>):<br>
    The target currency to which the conversion will be made.<br>
    Must be a valid ISO currency code supported by the external exchange rate API.<br>
    If the currency is invalid or unsupported, return a <code>400 Bad Request</code>.
  </li>

  <li>
    <strong>amount</strong> (<code>Number</code> / <code>Double</code>):<br>
    The numeric value to convert. Must be greater than zero.<br>
    If the value is zero or negative, return a <code>400 Bad Request</code>.
  </li>
</ul>

<h2>Validation Rules</h2>

<ul>
  <li><code>from !== "EUR"</code> → <code>400 Bad Request</code> with an error message.</li>
  <li><code>to</code> not in supported currencies → <code>400 Bad Request</code> with an error message.</li>
  <li><code>amount &lt;= 0</code> → <code>400 Bad Request</code> with an error message.</li>
</ul>

<p><strong>Note:</strong> A transaction should be created for every operation, whether successful or resulting in an error.</p>
            """,
            required = true,
            content = [Content(schema = Schema(implementation = CurrencyConverterDTO::class))]
        ))
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = """
<h2>Response</h2>

<ul>
  <li>
    <strong>data</strong> (<code>CurrencyConverterResponse</code>):<br>
    Contains the result of the conversion request. Only <code>EUR</code> is allowed as the base currency.<br>
    Any other value will result in a <code>400 Bad Request</code>.
  </li>

  <li>
    <strong>to</strong> (<code>String</code>):<br>
    The target currency to which the amount is converted.<br>
    Must be a valid ISO 4217 currency code supported by the external exchange rate API.<br>
    If unsupported or invalid, returns a <code>400 Bad Request</code>.
  </li>

  <li>
    <strong>amount</strong> (<code>Number</code> / <code>Double</code>):<br>
    The original amount to convert. Must be a number greater than zero.<br>
    If the amount is zero or negative, a <code>400 Bad Request</code> is returned.
  </li>
</ul>
            """,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CurrencyResponseDTO::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "404",
            description = "User Not Found",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CurrencyResponseDTO::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CurrencyResponseDTO::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CurrencyResponseDTO::class)
                )
            ]
        )
    ])
    fun postConvert(
        @PathVariable userId: UUID,
        @Valid @RequestBody request: CurrencyConverterDTO
    ): ResponseEntity<CurrencyResponseDTO<CurrencyConverterResponseDTO>> {
        var response: CurrencyResponseDTO<CurrencyConverterResponseDTO>
        try {
            response = currencyConverterService.convert(userId, request)
        } catch (e: Exception) {
            this.transactionService.registerTransactionFromConverterRequest(userId, request, null, e.message)
            val error = e.message ?: "General error"
            val code = when(e) {
                is OperationException -> "operation_error"
                is NotFoundException -> "not_found_error"
                is BadRequestException -> "bad_request_error"
                is ApiIntegrationException -> "api_integration_error"
                else -> "general_error"
            }
            val body = CurrencyConverterErrorDTO(ErrorDTO(code, error))
            this.logger.error { "Error reading response body: ${body}" }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CurrencyResponseDTO(null, body, HttpStatus.BAD_REQUEST.value()))
        }

        response.data?.let {
            this.transactionService.registerTransactionFromConverterRequest(userId, request, it, null)
            this.logger.info { "Success: ${it}" }
            return ResponseEntity.ok(CurrencyResponseDTO(it, null, HttpStatus.OK.value()))
        }

        this.transactionService.registerTransactionFromConverterRequest(userId, request, null, response.error?.toString())
        this.logger.error { "Error reading response body: ${response.error}" }
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @GetMapping("/{userId}/rates")
    @Operation(summary = "Get rates from external api")
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = """
<h2>Field Descriptions</h2>

<ul>
  <li>
    <strong>userId</strong> (<code>UUID</code>, <em>path parameter</em>):<br>
    The unique identifier of an existing user in the database. This value must match a registered user.
  </li>

  <li>
    <strong>base</strong> (<code>string</code>, <em>query parameter</em>):<br>
    The base currency from which exchange rates will be calculated.<br>
    <em>Note: Must be a valid and supported ISO currency code. If the value is invalid or unsupported, the API will return a 400 Bad Request.</em>
  </li>
</ul>
            """,
            content = [
                Content(
                    schema = Schema(implementation = CurrencyResponseRates::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "404",
            description = "User Not Found",
            content = [
                Content(
                    schema = Schema(implementation = CurrencyResponseRates::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = [
                Content(
                    schema = Schema(implementation = CurrencyResponseRates::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = [
                Content(
                    schema = Schema(implementation = CurrencyResponseRates::class)
                )
            ]
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