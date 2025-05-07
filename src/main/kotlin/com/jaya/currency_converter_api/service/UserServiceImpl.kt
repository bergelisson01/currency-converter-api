package com.jaya.currency_converter_api.service

import NotFoundException
import com.jaya.currency_converter_api.entity.model.User
import com.jaya.currency_converter_api.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl : UserService {

    @Autowired lateinit var userRepository: UserRepository

    override fun fetchAllUser(): List<User> {
        return this.userRepository.findAll()
    }

    override fun getUserById(id: UUID): User {
        val opt = this.userRepository.findById(id)
        if (opt.isPresent) return opt.get()
        throw NotFoundException("User not found for id ${id}.")
    }
}