package com.pereyrarg11.navigation.account.data.signin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteAccountDto(
    @SerialName("code")
    override val errorCode: Int?,
    @SerialName("message")
    override val errorMessage: String?
) : AuthDto
