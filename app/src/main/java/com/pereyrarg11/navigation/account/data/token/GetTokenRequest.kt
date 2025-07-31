package com.pereyrarg11.navigation.account.data.token


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO: move to core/session or split class into two classes
@Serializable
data class GetTokenRequest(
    @SerialName("grant_type")
    val grantType: String,
    @SerialName("phone_number")
    val phoneNumber: String? = null,
    @SerialName("code")
    val code: String? = null,
    @SerialName("refresh_token")
    val refreshToken: String? = null,
)
