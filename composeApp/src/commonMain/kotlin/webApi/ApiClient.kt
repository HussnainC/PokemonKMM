package webApi

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.logging.KtorSimpleLogger
import io.ktor.util.logging.Logger
import kotlinx.serialization.json.Json

 class ApiClient {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
        install(ResponseObserver) {
            onResponse { _ ->
            }
        }
        install(Logging) {
            logger = io.ktor.client.plugins.logging.Logger.DEFAULT
            level = LogLevel.ALL
            logger = object: io.ktor.client.plugins.logging.Logger {
                override fun log(message: String) {
                   KtorSimpleLogger("KtorClient").debug(message)
                }

            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }
}