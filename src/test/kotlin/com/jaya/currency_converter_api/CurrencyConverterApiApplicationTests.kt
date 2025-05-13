package com.jaya.currency_converter_api

import BadRequestException
import OperationException
import com.jaya.currency_converter_api.configuration.ConfigApiProviderProperties
import com.jaya.currency_converter_api.dto.CurrencyConverterDTO
import com.jaya.currency_converter_api.dto.CurrencyRatesDTO
import com.jaya.currency_converter_api.entity.enums.CurrencyConverterProviderEnum
import com.jaya.currency_converter_api.entity.model.User
import com.jaya.currency_converter_api.repository.UserRepository
import com.jaya.currency_converter_api.service.ClientApiServiceImpl
import com.jaya.currency_converter_api.service.CurrencyConverterApiServiceImpl
import com.jaya.currency_converter_api.service.internal.CurrencyApiService
import com.jaya.currency_converter_api.utils.CurrencyUtils

import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

@SpringBootTest
@ActiveProfiles("dev")
class CurrencyConverterApiApplicationTests {

	@Autowired
	lateinit var configApiProviderProperties: ConfigApiProviderProperties

	@Test
	fun contextLoads() {

	}

	@Test
	fun testConvertPositive() {
		// Given
		val userRepository = mock<UserRepository>()
		val clientApiService = mock<ClientApiServiceImpl>()
		val currencyApiService = mock<CurrencyApiService>()

		val user = mockUser()
		val userId = user.id ?: UUID.randomUUID()
		val rates = CurrencyRatesDTO(true, null, "EUR", null, mapOf("BRL" to 6.500129), null)
		val request = CurrencyConverterDTO("EUR", "BRL", 10.0)
		// "BRL": 6.500129 this is the value in sandbox mocked object.
		val expectedResult = CurrencyUtils.roundDouble(6, 10.0*6.500129)

		whenever(userRepository.findById(userId)).thenReturn(Optional.of(user))
		whenever(currencyApiService.getRates(request.to)).thenReturn(rates)

		val currencyConverterApiService = CurrencyConverterApiServiceImpl(
			this.configApiProviderProperties,
			userRepository,
			clientApiService
		)

		// When
		val result = currencyConverterApiService.convert(userId, request);

		// Then
		assertEquals(result.statusCode, 200)
		assertNotNull(result.data)
		assertEquals(result.data?.result, expectedResult)
	}

	@Test
	fun testConvertUnsupportedBaseCurrencyNegative() {
		// Given
		val userRepository = mock<UserRepository>()
		val clientApiService = mock<ClientApiServiceImpl>()
		val currencyApiService = mock<CurrencyApiService>()

		val user = mockUser()
		val userId = user.id ?: UUID.randomUUID()
		val rates = CurrencyRatesDTO(true, null, "EUR", null, mapOf("BRL" to 6.500129), null)
		val request = CurrencyConverterDTO("BRL", "EUR", 10.0)
		// "BRL": 6.500129 this is the value in sandbox mocked object.
		val expectedResult = "Not supported base currency BRL. Please Try [EUR]."

		whenever(userRepository.findById(userId)).thenReturn(Optional.of(user))
		whenever(currencyApiService.getRates(request.to)).thenReturn(rates)

		val currencyConverterApiService = CurrencyConverterApiServiceImpl(
			this.configApiProviderProperties,
			userRepository,
			clientApiService
		)

		assertFailsWith<OperationException> (
			message = expectedResult,
			block = { currencyConverterApiService.convert(userId, request) }
		)
	}

	@Test
	fun testConvertUnsupportedUnexistentCurrencyNegative() {
		// Given
		val userRepository = mock<UserRepository>()
		val clientApiService = mock<ClientApiServiceImpl>()
		val currencyApiService = mock<CurrencyApiService>()

		val user = mockUser()
		val userId = user.id ?: UUID.randomUUID()
		val rates = CurrencyRatesDTO(true, null, "EUR", null, mapOf("BRL" to 6.500129), null)
		val request = CurrencyConverterDTO("EUR", "BRA", 10.0)
		// "BRL": 6.500129 this is the value in sandbox mocked object.
		val expectedResult = "Rate not found for currency BRA."

		whenever(userRepository.findById(userId)).thenReturn(Optional.of(user))
		whenever(currencyApiService.getRates(request.to)).thenReturn(rates)

		val currencyConverterApiService = CurrencyConverterApiServiceImpl(
			this.configApiProviderProperties,
			userRepository,
			clientApiService
		)

		assertFailsWith<BadRequestException> (
			message = expectedResult,
			block = { currencyConverterApiService.convert(userId, request) }
		)
	}

	private fun mockUser() = User(
		UUID.randomUUID(),
		"",
		"",
		true,
		CurrencyConverterProviderEnum.SANDBOX_PROVIDER,
		""
	)

}
