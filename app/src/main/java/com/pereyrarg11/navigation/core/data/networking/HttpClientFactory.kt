package com.pereyrarg11.navigation.core.data.networking

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientFactory {
    fun build(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        // TODO: inject logger here
                        Log.d("HttpClientFactory", message)
                    }
                }
                level = LogLevel.ALL
            }
            // TODO: add basic auth by using https://ktor.io/docs/client-basic-auth.html#configure
            defaultRequest {
                contentType(ContentType.Application.Json)
                // TODO: get secrets and use them here
                header("x-api-key", "your_api_key")
            }
        }
    }
}
