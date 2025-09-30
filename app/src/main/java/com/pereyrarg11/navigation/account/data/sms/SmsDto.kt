package com.pereyrarg11.navigation.account.data.sms

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SmsDto(
    @SerialName("message")
    val message: String? = null,
    @SerialName("code")
    val code: Int? = null,
)
