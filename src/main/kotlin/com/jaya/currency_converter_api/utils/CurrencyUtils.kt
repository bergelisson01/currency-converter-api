package com.jaya.currency_converter_api.utils

import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

class CurrencyUtils {
    companion object {
        fun formatDate(format: String, date: Date): String {
            return SimpleDateFormat(format).format(date)
        }
        fun formatLocalDate(format: String, date: LocalDate): String {
            return LocalDate.now().format(DateTimeFormatter.ofPattern(format))
        }
        fun roundDouble(scale: Int, value: Double) = value.toBigDecimal().setScale(scale, RoundingMode.UP).toDouble()
    }
}