package com.pereyrarg11.navigation.auth.data.register


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("email")
    val email: String? = null,
    @SerialName("full_name")
    val fullName: String? = null,
    @SerialName("last_name")
    val lastName: String? = null,
    @SerialName("media_path")
    val mediaPath: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("phone")
    val phone: String? = null,
    @SerialName("phone_verification")
    val phoneVerification: Boolean? = null,
    @SerialName("status")
    val status: Int? = null,
    @SerialName("uuid")
    val uuid: String? = null,
    @SerialName("code")
    val errorCode: Int? = null,
    @SerialName("message")
    val errorMessage: String? = null,
)
