package com.jaya.currency_converter_api.controller

import BadRequestException
import NotFoundException
import OperationException
import com.jaya.currency_converter_api.dto.*
import com.jaya.currency_converter_api.entity.model.Transaction
import com.jaya.currency_converter_api.service.TransactionServiceImpl
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "Transactions", description = "Transactions API")
@RestController
@RequestMapping("/api/transactions")
class TransactionController(
    private val transactionService: TransactionServiceImpl
) {
    private val logger = KotlinLogging.logger(TransactionController::class.toString())

    @GetMapping("/{userId}/all")
    @Operation(summary = "Get transactions by User uuid")
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = [
                Content(
                    mediaType = "application/json",
                    array = ArraySchema(schema = Schema(implementation = Transaction::class))
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
    fun getAllTransactionsByUserId(@PathVariable userId: UUID): ResponseEntity<Any> {
        try {
            return  ResponseEntity.ok(
                CurrencyResponseDTO(transactionService.fetchAllTransactionByUserId(userId), null, HttpStatus.OK.value())
            )
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
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transactions by uuid")
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = [
                Content(
                    schema = Schema(implementation = Transaction::class)
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
    fun getTransactionById(@PathVariable id: UUID): ResponseEntity<Any> {
        try {
            val transaction = transactionService.getTransactionById(id)
            if (transaction != null) {
                return ResponseEntity.ok(
                    CurrencyResponseDTO(ResponseEntity.ok(transaction), null, HttpStatus.OK.value())
                )
            } else {
                return ResponseEntity.notFound().build()
            }
        } catch (e: Exception) {
            val error = e.message ?: "General error"
            val code = when(e) {
                is OperationException -> "operation_error"
                is NotFoundException -> "not_foundtá n_error"
                is BadRequestException -> "bar_redquest_error"
                else -> "general_error"
            }
            val body = CurrencyConverterErrorDTO(ErrorDTO(code, error))
            this.logger.error { "Error reading response body: ${body}" }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
        }
    }
}