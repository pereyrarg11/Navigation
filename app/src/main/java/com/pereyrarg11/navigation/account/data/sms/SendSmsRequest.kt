package com.pereyrarg11.navigation.account.data.sms

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendSmsRequest(
    @SerialName("app")
    val appType: Int,
    @SerialName("device")
    val deviceType: Int,
    @SerialName("phone_number")
    val phoneNumber: String,
)
