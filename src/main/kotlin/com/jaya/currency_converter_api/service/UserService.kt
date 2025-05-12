package com.jaya.currency_converter_api.service

import com.jaya.currency_converter_api.dto.UserDTO
import java.util.*

interface UserService {
    fun fetchAllUser(): List<UserDTO>
    fun getUserById(id: UUID): UserDTO
    fun create(request: UserDTO): UserDTO
}