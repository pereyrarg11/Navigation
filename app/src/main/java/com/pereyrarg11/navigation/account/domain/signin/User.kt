package com.pereyrarg11.navigation.account.domain.signin


data class User(
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val phone: String = "",
    val avatarUrl: String = "",
    val uuid: String = "",
)
