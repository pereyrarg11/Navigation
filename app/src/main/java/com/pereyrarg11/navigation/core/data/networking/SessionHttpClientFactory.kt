package com.pereyrarg11.navigation.core.data.networking

import android.util.Log
import com.pereyrarg11.navigation.account.data.token.GetTokenRequest
import com.pereyrarg11.navigation.account.data.token.TokenDto
import com.pereyrarg11.navigation.core.data.SecretValues
import com.pereyrarg11.navigation.core.domain.session.AuthInfo
import com.pereyrarg11.navigation.core.domain.session.SessionStorage
import com.pereyrarg11.navigation.core.domain.util.AppResult
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class SessionHttpClientFactory(
    private val sessionStorage: SessionStorage,
    private val secretValues: SecretValues,
) {
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
                        Log.d("UserHttpClientFactory", message)
                    }
                }
                level = LogLevel.ALL
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val info = sessionStorage.get()
                        BearerTokens(
                            accessToken = info?.accessToken.orEmpty(),
                            refreshToken = info?.refreshToken.orEmpty(),
                        )
                    }
                    sendWithoutRequest { request ->
                        // do you want to send Bearer token in current request?
                        val isOauth2Request = request.url.pathSegments.contains("oauth2")
                        // don't send bearer token for Oauth2 requests
                        !isOauth2Request
                    }
                    refreshTokens {
                        val info = sessionStorage.get()
                        val result = client.post<GetTokenRequest, TokenDto>(
                            baseUrl = secretValues.authUrl,
                            route = "/oauth2/token",
                            body = GetTokenRequest(
                                grantType = "refresh_token",
                                refreshToken = info?.refreshToken,
                            )
                        )
                        // set tokens if they were generated
                        if (result is AppResult.Success
                            && !result.data.accessToken.isNullOrEmpty()
                            && !result.data.refreshToken.isNullOrEmpty()
                        ) {
                            // refresh token success
                            val newAuthInfo = AuthInfo(
                                accessToken = result.data.accessToken,
                                refreshToken = result.data.refreshToken,
                            )
                            sessionStorage.set(newAuthInfo)
                            BearerTokens(
                                accessToken = newAuthInfo.accessToken,
                                refreshToken = newAuthInfo.refreshToken,
                            )
                        } else {
                            // refresh token error
                            BearerTokens(
                                accessToken = "",
                                refreshToken = "",
                            )
                        }
                    }
                }
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}
