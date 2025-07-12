package com.pereyrarg11.navigation.auth.domain.register

interface PatternValidator {
    fun matches(value: String): Boolean
}
