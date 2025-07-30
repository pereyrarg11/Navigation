package com.pereyrarg11.navigation.auth.domain.register


data class User(
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val phone: String = "",
    val avatarUrl: String = "",
    val uuid: String = "",
)
