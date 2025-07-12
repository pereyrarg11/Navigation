package com.pereyrarg11.navigation.auth.domain

import com.pereyrarg11.navigation.auth.domain.register.User
import com.pereyrarg11.navigation.core.domain.util.AppResult
import com.pereyrarg11.navigation.core.domain.util.DataError

interface AuthRepository {
    suspend fun registerUser(
        name: String,
        lastName: String,
        phoneNumber: String,
        email: String,
    ): AppResult<User, DataError.Network>
}
