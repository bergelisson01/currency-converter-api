package com.jaya.currency_converter_api.service

import com.jaya.currency_converter_api.dto.ConvertResponse
import com.jaya.currency_converter_api.dto.CurrencyConverterDTO
import com.jaya.currency_converter_api.dto.CurrencyResponseDTO
import com.jaya.currency_converter_api.entity.model.Transaction
import java.util.*

interface TransactionService {

    fun saveTransaction(transaction: Transaction): Transaction
    fun fetchAllTransactionByUserId(userId: UUID): List<Transaction>
    fun getTransactionById(id: UUID): Transaction
    fun registerTransactionFromConverterRequest(
        userId: UUID,
        request: CurrencyConverterDTO,
        response: ConvertResponse?,
        error: String?
    )
}