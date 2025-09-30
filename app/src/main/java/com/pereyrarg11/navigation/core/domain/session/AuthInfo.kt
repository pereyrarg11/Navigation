package com.pereyrarg11.navigation.core.domain.session

data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
)
