package com.jaya.currency_converter_api.entity.model

import com.jaya.currency_converter_api.entity.enums.CurrencyConverterProviderEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "email", nullable = true)
    val email: String?,

    @Column(name = "active", nullable = false)
    val active: Boolean,

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    val provider: CurrencyConverterProviderEnum,
) {
    constructor() : this(null, "Admin", "admin@admin.com", true, CurrencyConverterProviderEnum.SANDBOX_PROVIDER)
    constructor(name: String, email: String, provider: CurrencyConverterProviderEnum) : this(
        null,
        name,
        email,
        true,
        provider
    )
    constructor(name: String, provider: CurrencyConverterProviderEnum) : this(
        null,
        name,
        null,
        true,
        provider
    )
}