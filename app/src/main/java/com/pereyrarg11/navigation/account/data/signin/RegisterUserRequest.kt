package com.pereyrarg11.navigation.account.data.signin


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterUserRequest(
    @SerialName("name")
    val name: String,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("email")
    val email: String,
)
