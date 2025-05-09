package com.jaya.currency_converter_api.utils

import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.Date

class CurrencyUtils {
    companion object {
        fun formatDate(format: String, date: Date): String {
            return SimpleDateFormat(format).format(date)
        }

        fun roundDouble(scale: Int, value: Double) = value.toBigDecimal().setScale(scale, RoundingMode.UP).toDouble()
    }
}