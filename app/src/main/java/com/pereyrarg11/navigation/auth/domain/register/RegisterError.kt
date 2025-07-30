package com.pereyrarg11.navigation.auth.domain.register

import com.pereyrarg11.navigation.core.domain.util.AppError

sealed interface RegisterError : AppError {
    enum class PhoneNumber : RegisterError {
        EMPTY,
        LENGTH,
        DIGITS
    }

    enum class Email : RegisterError {
        EMPTY,
        PATTERN,
        TOO_LONG,
    }

    enum class Name : RegisterError {
        EMPTY,
        PATTERN,
        TOO_LONG
    }

    enum class LastName : RegisterError {
        EMPTY,
        PATTERN,
        TOO_LONG
    }
}
