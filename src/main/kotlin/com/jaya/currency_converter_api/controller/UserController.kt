package com.jaya.currency_converter_api.controller

import com.jaya.currency_converter_api.configuration.ConfigApiProviderProperties
import com.jaya.currency_converter_api.dto.CurrencyResponseDTO
import com.jaya.currency_converter_api.dto.UserDTO
import com.jaya.currency_converter_api.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@Tag(name = "Users", description = "Users API")
@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
    private val configApiProviderProperties: ConfigApiProviderProperties
) {

    @GetMapping
    @Operation(summary = "List All Users")
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
            description = "Users Not Found"
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
                    schema = Schema(implementation = UserDTO::class)
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
    fun getUserById(@PathVariable id: UUID): ResponseEntity<CurrencyResponseDTO<UserDTO>> {
        val user = userService.getUserById(id)
        if (user != null) {
            return ResponseEntity.ok(CurrencyResponseDTO(user, null, HttpStatus.OK.value()))
        } else {
            return ResponseEntity.notFound().build()
        }
    }
}