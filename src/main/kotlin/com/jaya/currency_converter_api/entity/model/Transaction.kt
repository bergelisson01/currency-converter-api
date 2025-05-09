package com.jaya.currency_converter_api.entity.model

import com.jaya.currency_converter_api.entity.enums.CurrencyConverterProviderEnum
import com.jaya.currency_converter_api.entity.enums.CurrencyConverterStatusEnum
import jakarta.persistence.*
import java.time.LocalDate
import java.util.UUID

@Entity
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uid: UUID?,
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    val user: User?,
    @Column(name = "origin_currency", nullable = false)
    var originCurrency: String,
    @Column(name = "origin_value", nullable = false)
    var originValue: Double,
    @Column(name = "destination_currency", nullable = false)
    var destinationCurrency: String,
    @Column(name = "converted_value", nullable = true)
    var convertedValue: Double?,
    @Column(name = "conversion_rate", nullable = true)
    var conversionRate: Double?,
    @Column(name = "date", nullable = false)
    var date: LocalDate,
    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    var provider: CurrencyConverterProviderEnum,
    @Column(name = "status", nullable = true)
    var status: CurrencyConverterStatusEnum?,
    @Column(name = "error", nullable = true)
    var error: String?
)