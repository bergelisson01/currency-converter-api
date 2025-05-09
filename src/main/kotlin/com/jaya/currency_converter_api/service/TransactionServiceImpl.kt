package com.jaya.currency_converter_api.service

import NotFoundException
import com.jaya.currency_converter_api.dto.ConvertResponse
import com.jaya.currency_converter_api.dto.CurrencyConverterDTO
import com.jaya.currency_converter_api.dto.CurrencyResponseDTO
import com.jaya.currency_converter_api.entity.enums.CurrencyConverterProviderEnum
import com.jaya.currency_converter_api.entity.enums.CurrencyConverterStatusEnum
import com.jaya.currency_converter_api.entity.model.Transaction
import com.jaya.currency_converter_api.entity.model.User
import com.jaya.currency_converter_api.repository.TransactionRepository
import com.jaya.currency_converter_api.repository.UserRepository
import com.jaya.currency_converter_api.service.internal.ExchangeRatesApiServiceImpl
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class TransactionServiceImpl : TransactionService {
    private val logger = KotlinLogging.logger(TransactionServiceImpl::class.toString())

    @Autowired lateinit var transactionRepository: TransactionRepository
    @Autowired lateinit var userRepository: UserRepository

    override fun saveTransaction(transaction: Transaction): Transaction {
        return this.transactionRepository.save(transaction)
    }

    override fun fetchAllTransactionByUserId(userId: UUID): List<Transaction> {
        return this.transactionRepository.findByUserId(userId)
    }

    override fun getTransactionById(id: UUID): Transaction {
        val opt = this.transactionRepository.findById(id)
        if (opt.isPresent) return opt.get()
        throw NotFoundException("Transaction with uuid $id not found")
    }

    override fun registerTransactionFromConverterRequest(
        userId: UUID,
        request: CurrencyConverterDTO,
        response: ConvertResponse?,
        error: String?
    ) {
        val user: User? = this.userRepository.findById(userId).orElse(null)
        val transaction = if (response?.success == true) {
            Transaction(
                null,
                user,
                request.from,
                request.amount,
                request.to,
                response?.result,
                response?.info?.rate,
                LocalDate.now(),
                user?.provider ?: CurrencyConverterProviderEnum.NONE,
                CurrencyConverterStatusEnum.SUCCESS,
                null
            )
        } else {
            Transaction(
                null,
                user,
                request.from,
                request.amount,
                request.to,
                null,
                null,
                LocalDate.now(),
                user?.provider ?: CurrencyConverterProviderEnum.NONE,
                CurrencyConverterStatusEnum.ERROR,
                error
            )
        }
        this.saveTransaction(transaction)
        this.logger.info { "Transaction registered successfuly: ${transaction}" }
    }
}