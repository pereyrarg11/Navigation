package com.pereyrarg11.navigation.account.domain

import com.pereyrarg11.navigation.core.domain.util.AppError

sealed interface AccountFormError : AppError {
    enum class PhoneNumber : AccountFormError {
        EMPTY,
        LENGTH,
        DIGITS
    }

    enum class Email : AccountFormError {
        EMPTY,
        PATTERN,
        TOO_LONG,
    }

    enum class Name : AccountFormError {
        EMPTY,
        PATTERN,
        TOO_LONG
    }

    enum class LastName : AccountFormError {
        EMPTY,
        PATTERN,
        TOO_LONG
    }

    enum class OtpCode : AccountFormError {
        LENGTH,
    }
}
