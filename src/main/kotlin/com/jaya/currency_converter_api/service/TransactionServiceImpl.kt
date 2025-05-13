package com.jaya.currency_converter_api.service

import NotFoundException
import com.jaya.currency_converter_api.dto.CurrencyConverterDTO
import com.jaya.currency_converter_api.dto.CurrencyConverterResponseDTO
import com.jaya.currency_converter_api.dto.TransactionDTO
import com.jaya.currency_converter_api.entity.enums.CurrencyConverterProviderEnum
import com.jaya.currency_converter_api.entity.enums.CurrencyConverterStatusEnum
import com.jaya.currency_converter_api.entity.model.Transaction
import com.jaya.currency_converter_api.entity.model.User
import com.jaya.currency_converter_api.repository.TransactionRepository
import com.jaya.currency_converter_api.repository.UserRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*
import java.util.stream.Collectors

@Service
class TransactionServiceImpl : TransactionService {
    private val logger = KotlinLogging.logger(TransactionServiceImpl::class.toString())

    @Autowired lateinit var transactionRepository: TransactionRepository
    @Autowired lateinit var userRepository: UserRepository

    override fun saveTransaction(transaction: Transaction): TransactionDTO {
        return TransactionDTO(this.transactionRepository.save(transaction))
    }

    override fun fetchAllTransactionByUserId(userId: UUID): List<TransactionDTO> {
        return this.transactionRepository.findByUserId(userId).stream().map { TransactionDTO(it) }.collect(Collectors.toList())
    }

    override fun getTransactionById(id: UUID): TransactionDTO {
        val opt = this.transactionRepository.findById(id)
        if (opt.isPresent) return TransactionDTO(opt.get())
        throw NotFoundException("Transaction with uuid $id not found")
    }

    override fun registerTransactionFromConverterRequest(
        userId: UUID,
        request: CurrencyConverterDTO,
        response: CurrencyConverterResponseDTO?,
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
                Date(),
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
                Date(),
                user?.provider ?: CurrencyConverterProviderEnum.NONE,
                CurrencyConverterStatusEnum.ERROR,
                error
            )
        }
        this.saveTransaction(transaction)
        this.logger.info { "Transaction registered successfuly: ${transaction}" }
    }
}