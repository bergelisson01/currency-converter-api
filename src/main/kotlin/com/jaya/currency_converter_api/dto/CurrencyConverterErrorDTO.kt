package com.jaya.currency_converter_api.dto

data class CurrencyConverterErrorDTO(
    val error: ErrorDTO
) {
    override fun toString(): String {
        return """
        {
          "error": {
            "code": "${error.code}",
            "message": "${error.message}"
          }
        }
    """.trimIndent()
    }
}

data class ErrorDTO(
    val code: String,
    val message: String
)