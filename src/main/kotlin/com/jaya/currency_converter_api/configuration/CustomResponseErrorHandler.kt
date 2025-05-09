import com.jaya.currency_converter_api.dto.CurrencyConverterErrorDTO
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.DefaultResponseErrorHandler
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.util.StreamUtils
import java.nio.charset.Charset

class CustomResponseErrorHandler : DefaultResponseErrorHandler() {

    override fun hasError(response: ClientHttpResponse): Boolean {
        return response.statusCode.is4xxClientError || response.statusCode.is5xxServerError
    }

    override fun handleError(response: ClientHttpResponse) {
        val body = this.getResponseBodyAsString(response)
        throw ApiIntegrationException(body, response.statusCode.value())
    }

    private fun getResponseBodyAsString(response: ClientHttpResponse): String {
        return try {
            StreamUtils.copyToString(response.body, Charset.defaultCharset())
        } catch (e: Exception) {
            "Error reading response body: ${e.message}"
        }
    }
}

class NotFoundException(message: String?) : RuntimeException(message)
class BadRequestException(message: String?) : RuntimeException(message)
class OperationException(message: String?) : RuntimeException(message)
class ApiIntegrationException(message: String?, val statusCode: Int) : RuntimeException(message)
