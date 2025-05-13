package com.jaya.currency_converter_api.service.internal

import BadRequestException
import OperationException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.jaya.currency_converter_api.configuration.ConfigApiProvider
import com.jaya.currency_converter_api.dto.*
import com.jaya.currency_converter_api.service.ClientApiService
import com.jaya.currency_converter_api.utils.CurrencyUtils
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpStatus
import java.util.Date

@Primary
class SandboxCurrencyApiServiceImpl : CurrencyApiService {
    private val ALLOWED_BASE_CURRENCY = listOf("EUR")
    var providerConfig: ConfigApiProvider
    var clientApiService: ClientApiService

    constructor(config: ConfigApiProvider, clientApiService: ClientApiService) {
        this.providerConfig = config
        this.clientApiService = clientApiService
    }

    override fun convert(request: CurrencyConverterDTO): CurrencyResponseDTO<CurrencyConverterResponseDTO> {
        if (request.from.isEmpty() || !ALLOWED_BASE_CURRENCY.contains(request.from)) {
            throw OperationException("Not supported base currency ${request.from}. Please Try ${ALLOWED_BASE_CURRENCY}.")
        }

        val ratesResponse = this.getRates(request.from)

        if (!ratesResponse.success) {
            throw BadRequestException("Rates not found for current request.")
        }

        val rate = ratesResponse.rates?.get(request.to) ?: throw BadRequestException("Rate not found for currency ${request.to}.")

        val query = Query(request.from, request.to, request.amount)
        val info = Info(Date().time, rate)
        val result = CurrencyUtils.roundDouble(6, rate * request.amount)
        val date = CurrencyUtils.formatDate("dd-MM-yyyy HH:mm:ss.SSS", Date())
        val convertResponse = CurrencyConverterResponseDTO(true, query, info, date, result)

        return CurrencyResponseDTO(convertResponse, null, HttpStatus.OK.value())
    }

    override fun getRates(baseCurrency: String): CurrencyRatesDTO {
        val processedBase = baseCurrency.trim().uppercase()
        if (processedBase.isEmpty() || !ALLOWED_BASE_CURRENCY.contains(processedBase)) {
            throw OperationException("Not supported base currency ${baseCurrency}. Please Try EUR.")
        }

        return mockRatesResponse()
    }

    private fun mockRatesResponse(): CurrencyRatesDTO {
        val mapper = ObjectMapper().registerModule(KotlinModule.Builder().build())

        try {
            return mapper.readValue(ratesResponse, CurrencyRatesDTO::class.java)
        } catch (e: Exception) {
            throw OperationException("Rates not found.")
        }
    }

    private fun mockRatesResponse(code: Int, info: String): CurrencyRatesDTO {
        return CurrencyRatesDTO(
            false,
            null,
            null,
            null,
            null,
            CurrencyError(code, info)
        )
    }

    private val error = """
        {
          "success": false,
          "error": {
            "code": 104,
            "info": "Your monthly API request volume has been reached. Please upgrade your plan."
          }
        }
        """.trimIndent()

    private val ratesResponse = """
        {
            "success": true,
            "timestamp": 1746647644,
            "base": "EUR",
            "date": "2025-05-07",
            "rates": {
                "AED": 4.152648,
                "AFN": 80.834283,
                "ALL": 97.911278,
                "AMD": 440.118399,
                "ANG": 2.037661,
                "AOA": 1034.494636,
                "ARS": 1294.526025,
                "AUD": 1.757958,
                "AWG": 2.037898,
                "AZN": 1.918624,
                "BAM": 1.945382,
                "BBD": 2.282727,
                "BDT": 137.363493,
                "BGN": 1.95009,
                "BHD": 0.426178,
                "BIF": 3316.601456,
                "BMD": 1.130595,
                "BND": 1.459693,
                "BOB": 7.812748,
                "BRL": 6.500129,
                "BSD": 1.13062,
                "BTC": 1.175453e-5,
                "BTN": 95.77346,
                "BWP": 15.330056,
                "BYN": 3.699964,
                "BYR": 22159.66884,
                "BZD": 2.270989,
                "CAD": 1.563676,
                "CDF": 3250.461759,
                "CHF": 0.930822,
                "CLF": 0.027887,
                "CLP": 1070.142601,
                "CNY": 8.168947,
                "CNH": 8.171536,
                "COP": 4859.016161,
                "CRC": 572.173807,
                "CUC": 1.130595,
                "CUP": 29.960777,
                "CVE": 110.121853,
                "CZK": 24.895245,
                "DJF": 200.929072,
                "DKK": 7.460343,
                "DOP": 66.568353,
                "DZD": 149.883185,
                "EGP": 57.25086,
                "ERN": 16.95893,
                "ETB": 149.973501,
                "EUR": 1,
                "FJD": 2.559101,
                "FKP": 0.845789,
                "GBP": 0.850507,
                "GEL": 3.114793,
                "GGP": 0.845789,
                "GHS": 15.093482,
                "GIP": 0.845789,
                "GMD": 80.83483,
                "GNF": 9785.871176,
                "GTQ": 8.698418,
                "GYD": 236.531093,
                "HKD": 8.775144,
                "HNL": 29.294506,
                "HRK": 7.531575,
                "HTG": 147.768461,
                "HUF": 404.264155,
                "IDR": 18667.485926,
                "ILS": 4.055955,
                "IMP": 0.845789,
                "INR": 95.832663,
                "IQD": 1481.079907,
                "IRR": 47612.198856,
                "ISK": 146.49103,
                "JEP": 0.845789,
                "JMD": 179.419161,
                "JOD": 0.801821,
                "JPY": 162.601662,
                "KES": 146.129697,
                "KGS": 98.870194,
                "KHR": 4526.101752,
                "KMF": 490.115781,
                "KPW": 1017.509861,
                "KRW": 1575.648537,
                "KWD": 0.346788,
                "KYD": 0.942125,
                "KZT": 581.703406,
                "LAK": 24448.907442,
                "LBP": 101300.026222,
                "LKR": 338.489748,
                "LRD": 226.116086,
                "LSL": 20.677665,
                "LTL": 3.338354,
                "LVL": 0.683885,
                "LYD": 6.171948,
                "MAD": 10.419998,
                "MDL": 19.326952,
                "MGA": 5025.496431,
                "MKD": 61.467661,
                "MMK": 2373.658099,
                "MNT": 4043.24489,
                "MOP": 9.037501,
                "MRU": 44.790136,
                "MUR": 51.374509,
                "MVR": 17.422921,
                "MWK": 1960.448645,
                "MXN": 22.159445,
                "MYR": 4.792593,
                "MZN": 72.253585,
                "NAD": 20.67903,
                "NGN": 1818.867971,
                "NIO": 41.603649,
                "NOK": 11.699146,
                "NPR": 153.237536,
                "NZD": 1.902193,
                "OMR": 0.435272,
                "PAB": 1.13063,
                "PEN": 4.131648,
                "PGK": 4.556654,
                "PHP": 62.772935,
                "PKR": 318.114312,
                "PLN": 4.272365,
                "PYG": 9035.611798,
                "QAR": 4.121528,
                "RON": 5.117529,
                "RSD": 116.603292,
                "RUB": 91.159116,
                "RWF": 1624.124468,
                "SAR": 4.240701,
                "SBD": 9.441431,
                "SCR": 16.070076,
                "SDG": 678.924164,
                "SEK": 10.919793,
                "SGD": 1.463533,
                "SHP": 0.88847,
                "SLE": 25.698921,
                "SLL": 23708.000811,
                "SOS": 646.082906,
                "SRD": 41.627431,
                "STD": 23401.041061,
                "SVC": 9.892025,
                "SYP": 14699.599927,
                "SZL": 20.666695,
                "THB": 37.166102,
                "TJS": 11.729766,
                "TMT": 3.96839,
                "TND": 3.387758,
                "TOP": 2.647961,
                "TRY": 43.688121,
                "TTD": 7.672421,
                "TWD": 34.318994,
                "TZS": 3042.432413,
                "UAH": 46.850602,
                "UGX": 4136.846044,
                "USD": 1.130595,
                "UYU": 47.3365,
                "UZS": 14612.94511,
                "VES": 100.184801,
                "VND": 29353.081748,
                "VUV": 136.426244,
                "WST": 3.011898,
                "XAF": 652.511676,
                "XAG": 0.034914,
                "XAU": 0.000335,
                "XCD": 3.05549,
                "XDR": 0.812502,
                "XOF": 651.223074,
                "XPF": 119.331742,
                "YER": 276.42779,
                "ZAR": 20.653373,
                "ZMK": 10176.711868,
                "ZMW": 30.214465,
                "ZWL": 364.051241
            }
        }
        """.trimIndent()
}