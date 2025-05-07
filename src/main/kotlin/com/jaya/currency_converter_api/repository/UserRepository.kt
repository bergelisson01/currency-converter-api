package com.jaya.currency_converter_api.repository

import com.jaya.currency_converter_api.entity.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID>{
}