package com.jaya.currency_converter_api.controller

import com.jaya.currency_converter_api.configuration.ConfigApiProviderProperties
import com.jaya.currency_converter_api.dto.*
import com.jaya.currency_converter_api.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@Tag(name = "Users", description = "Users API")
@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
    private val configApiProviderProperties: ConfigApiProviderProperties
) {
    private val logger = KotlinLogging.logger(UserController::class.toString())

    @GetMapping
    @Operation(summary = "List All Users")
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = [
                Content(
                    schema = Schema(implementation = CurrencyResponseListUser::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Users Not Found",
            content = [
                Content(
                    schema = Schema(implementation = CurrencyResponseListUser::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = [
                Content(
                    schema = Schema(implementation = CurrencyResponseListUser::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = [
                Content(
                    schema = Schema(implementation = CurrencyResponseListUser::class)
                )
            ]
        )
    ])
    fun getAllUsers(): ResponseEntity<CurrencyResponseDTO<List<UserDTO>>> {
        val users = userService.fetchAllUser()
        return ResponseEntity.ok(CurrencyResponseDTO(users, null, HttpStatus.OK.value()))
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get User by uuid")
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = [
                Content(
                    schema = Schema(implementation = CurrencyResponseUser::class)
                )
            ]),
        ApiResponse(
            responseCode = "404",
            description = "User Not Found",
            content = [
                Content(
                    schema = Schema(implementation = CurrencyResponseUser::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = [
                Content(
                    schema = Schema(implementation = CurrencyResponseUser::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = [
                Content(
                    schema = Schema(implementation = CurrencyResponseUser::class)
                )
            ]
        )
    ])
    fun getUserById(@PathVariable id: UUID): ResponseEntity<CurrencyResponseDTO<UserDTO>> {
        val user = userService.getUserById(id)
        if (user != null) {
            return ResponseEntity.ok(CurrencyResponseDTO(user, null, HttpStatus.OK.value()))
        } else {
            return ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/")
    @Operation(
        summary = "Create User",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = """
<h2>Field Descriptions</h2>

<ul>
  <li>
    <strong>name</strong> (<code>string</code>):<br>
    The name of the external API provider.
  </li>

  <li>
    <strong>email</strong> (<code>string</code>, <em>optional</em>):<br>
    The email address of the user.<br>
    <em>Note: This field is not used as a primary key.</em>
  </li>

  <li>
    <strong>provider</strong> (<code>string</code>):<br>
    A unique key identifying the external API provider.<br>
    Supported values: <code>SANDBOX_PROVIDER</code>, <code>EXCHANGE_RATES_API_PROVIDER</code>.
  </li>

  <li>
    <strong>accessKey</strong> (<code>string</code>, <em>optional</em>):<br>
    The access key used to authenticate with the external API, typically included in the request header if required.
  </li>
</ul>
            """,
            required = true,
            content = [Content(schema = Schema(implementation = UserDTO::class))]
        ))
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = [
                Content(
                    schema = Schema(implementation = CurrencyResponseUser::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "404",
            description = "User Not Found",
            content = [
                Content(
                    schema = Schema(implementation = CurrencyResponseUser::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = [
                Content(
                    schema = Schema(implementation = CurrencyResponseUser::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = [
                Content(
                    schema = Schema(implementation = CurrencyResponseUser::class)
                )
            ]
        )
    ])
    fun postConvert(
        @Valid @RequestBody request: UserDTO
    ): ResponseEntity<Any> {
        try {
            val user = userService.create(request)
            return ResponseEntity.ok(CurrencyResponseDTO(user, null, HttpStatus.OK.value()))
        } catch (e: Exception) {
            val error = e.message ?: "General error"
            val code = "general_error"
            val body = CurrencyConverterErrorDTO(ErrorDTO(code, error))
            this.logger.error { "Error reading response body: ${body}" }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
        }
    }
}