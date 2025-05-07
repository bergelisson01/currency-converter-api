package com.jaya.currency_converter_api.configuration

import com.jaya.currency_converter_api.entity.model.User
import com.jaya.currency_converter_api.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer(private val userRepository: UserRepository) : CommandLineRunner {

    override fun run(vararg args: String) {
        userRepository.deleteAll()
        userRepository.save(User())
    }
}