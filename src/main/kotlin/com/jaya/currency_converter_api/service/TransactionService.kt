package com.jaya.currency_converter_api.service

import com.jaya.currency_converter_api.dto.CurrencyConverterDTO
import com.jaya.currency_converter_api.dto.CurrencyConverterResponseDTO
import com.jaya.currency_converter_api.dto.TransactionDTO
import com.jaya.currency_converter_api.entity.model.Transaction
import java.util.*

interface TransactionService {

    fun saveTransaction(transaction: Transaction): TransactionDTO
    fun fetchAllTransactionByUserId(userId: UUID): List<TransactionDTO>
    fun getTransactionById(id: UUID): TransactionDTO
    fun registerTransactionFromConverterRequest(
        userId: UUID,
        request: CurrencyConverterDTO,
        response: CurrencyConverterResponseDTO?,
        error: String?
    )
}