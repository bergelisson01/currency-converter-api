package com.jaya.currency_converter_api.service

import ApiIntegrationException
import BadRequestException
import NotFoundException
import com.jaya.currency_converter_api.dto.CurrencyResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.sql.DriverManager.println

@Service
class ClientApiServiceImpl : ClientApiService {

    @Autowired
    lateinit var restTemplate: RestTemplate

    override fun <T> get(path: String, params: MultiValueMap<String, String>?, clazz: ParameterizedTypeReference<T>): CurrencyResponseDTO<T> {
        var url = path
        params?.let {
            url = this.buildUri(path, it)
        }
        val requestEntity = this.buildRequestEntity();
        return try {
            val response: ResponseEntity<T> = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                clazz
            )
            CurrencyResponseDTO(response.body, null, HttpStatus.OK.value())
        } catch (e: ApiIntegrationException) {
            println("Generic exception with status code ${e.statusCode}: ${e.message}")
            CurrencyResponseDTO(null, e.message, e.statusCode)
        } catch (e: NotFoundException) {
            println("Resource not found: ${e.message}")
            CurrencyResponseDTO(null, e.message, HttpStatus.NOT_FOUND.value())
        } catch (e: BadRequestException) {
            println("Bad Request: ${e.message}")
            CurrencyResponseDTO(null, e.message, HttpStatus.BAD_REQUEST.value())
        } catch (e: Exception) {
            println("Bad Request: ${e.message}")
            CurrencyResponseDTO(null, e.message, HttpStatus.BAD_REQUEST.value())
        }
    }

    private fun buildUri(url: String, params: MultiValueMap<String, String>): String {
        val builder = UriComponentsBuilder.fromUriString(url)
        builder.queryParams(params)
        return builder.build().toUriString()
    }

    private fun buildRequestEntity(): HttpEntity<Any> {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        return HttpEntity<Any>(headers)
    }
}