package com.jaya.currency_converter_api.service

import NotFoundException
import com.jaya.currency_converter_api.dto.UserDTO
import com.jaya.currency_converter_api.entity.model.User
import com.jaya.currency_converter_api.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl : UserService {

    @Autowired lateinit var userRepository: UserRepository

    override fun fetchAllUser(): List<UserDTO> {
        return this.userRepository.findAll().map { UserDTO(it) }
    }

    override fun getUserById(id: UUID): UserDTO {
        val opt = this.userRepository.findById(id)
        if (opt.isPresent) return UserDTO(opt.get())
        throw NotFoundException("User not found for id ${id}.")
    }
}