package com.jaya.currency_converter_api.service

import com.jaya.currency_converter_api.entity.model.User
import java.util.*

interface UserService {
    fun fetchAllUser(): List<User>
    fun getUserById(id: UUID): User
}