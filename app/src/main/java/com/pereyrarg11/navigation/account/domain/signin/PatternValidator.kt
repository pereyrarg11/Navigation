package com.pereyrarg11.navigation.account.domain.signin

interface PatternValidator {
    fun matches(value: String): Boolean
}
