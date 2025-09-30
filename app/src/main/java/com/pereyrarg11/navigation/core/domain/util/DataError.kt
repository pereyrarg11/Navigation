package com.pereyrarg11.navigation.core.domain.util

sealed interface DataError : AppError {

    enum class Network : DataError {
        UNAUTHORIZED,
        CONFLICT,
        NO_INTERNET,
        SERVER_ERROR,
        SERIALIZATION,
        OTP_NOT_ACTIVE,
        USER_NOT_FOUND,
        USER_NOT_ACTIVE,
        UNKNOWN,
    }

    enum class Local : DataError {
        DISK_FULL,
    }
}
