package com.pereyrarg11.navigation.account.data.token


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenDto(
    @SerialName("access_token")
    val accessToken: String? = null,
    @SerialName("refresh_token")
    val refreshToken: String? = null,
    @SerialName("code")
    val errorCode: Int? = null,
    @SerialName("message")
    val errorMessage: String? = null,
)
