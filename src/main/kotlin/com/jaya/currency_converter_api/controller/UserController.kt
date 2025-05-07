package com.jaya.currency_converter_api.controller

import com.jaya.currency_converter_api.configuration.ConfigApiProvider
import com.jaya.currency_converter_api.configuration.ConfigApiProviderProperties
import com.jaya.currency_converter_api.dto.CurrencyConverterRequestDTO
import com.jaya.currency_converter_api.entity.model.User
import com.jaya.currency_converter_api.service.CurrencyConverterApiServiceImpl
import com.jaya.currency_converter_api.service.TestApiServiceImpl
import com.jaya.currency_converter_api.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
    private val configApiProviderProperties: ConfigApiProviderProperties
) {

    @GetMapping
    fun getAllUsers(): List<User> = userService.fetchAllUser()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: UUID): ResponseEntity<User> {
        val user = userService.getUserById(id)
        if (user != null) {
            return ResponseEntity.ok(user)
        } else {
            return ResponseEntity.notFound().build()
        }
    }
}