package com.jaya.currency_converter_api.controller

import ApiIntegrationException
import BadRequestException
import NotFoundException
import OperationException
import com.jaya.currency_converter_api.dto.*
import com.jaya.currency_converter_api.entity.model.Transaction
import com.jaya.currency_converter_api.service.CurrencyConverterApiServiceImpl
import com.jaya.currency_converter_api.service.TransactionServiceImpl
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
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
    @PostMapping("/{userId}/convert")
    @Operation(
        summary = "Currency Convert",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Currency converter request",
            required = true,
            content = [Content(schema = Schema(implementation = CurrencyConverterDTO::class))]
        ))
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Successful operation",
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
        var response: CurrencyResponseDTO<ConvertResponse>
        try {
            response = currencyConverterService.convert(userId, request)
        } catch (e: ApiIntegrationException) {
            this.transactionService.registerTransactionFromConverterRequest(userId, request, null, e.message)
            println("Error reading response body: ${e.message}")
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
            println("Error reading response body: ${e.message}")
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CurrencyConverterErrorDTO(ErrorDTO(code, error)))
        }

        response.data?.let {
            this.transactionService.registerTransactionFromConverterRequest(userId, request, it, null)
            return ResponseEntity.ok(it)
        }

        this.transactionService.registerTransactionFromConverterRequest(userId, request, null, response.error)
        return ResponseEntity.status(response.statusCode).body(response.error)
    }

    @GetMapping("/{userId}/rates")
    @Operation(summary = "Get rates from external api")
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Successful operation",
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
        } catch (e: Exception) {
            val error = e.message ?: "General error"
            val code = when(e) {
                is OperationException -> "operation_error"
                is NotFoundException -> "not_found_error"
                is BadRequestException -> "bar_redquest_error"
                else -> "general_error"
            }
            println("Error reading response body: ${e.message}")
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CurrencyConverterErrorDTO(ErrorDTO(code, error)))
        }

        response.data?.let {
            return ResponseEntity.ok(it)
        }

        return ResponseEntity.status(response.statusCode).body(response.error)
    }
}