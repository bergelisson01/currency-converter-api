package com.jaya.currency_converter_api.entity.model

import com.jaya.currency_converter_api.dto.UserDTO
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

    @Column(name = "access_key", nullable = true)
    val accessKey: String?,
) {
    constructor() : this(
        null,
        "Admin",
        "admin@admin.com",
        true,
        CurrencyConverterProviderEnum.SANDBOX_PROVIDER,
        "212c807c558aaa2a2dba5a98696d08e5"

    )
    constructor(name: String, email: String, provider: CurrencyConverterProviderEnum, accessKey: String?) : this(
        null,
        name,
        email,
        true,
        provider,
        accessKey
    )
    constructor(name: String, provider: CurrencyConverterProviderEnum, accessKey: String?) : this(
        null,
        name,
        null,
        true,
        provider,
        accessKey
    )
    constructor(request: UserDTO) : this(
        null,
        request.name,
        request.email,
        true,
        request.provider,
        request.accessKey
    )
}