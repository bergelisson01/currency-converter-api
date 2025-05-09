package com.jaya.currency_converter_api.repository

import com.jaya.currency_converter_api.entity.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TransactionRepository : JpaRepository<Transaction, UUID>{
    fun findByUserId(userId: UUID): List<Transaction>
}