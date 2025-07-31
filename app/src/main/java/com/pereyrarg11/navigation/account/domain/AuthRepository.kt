package com.pereyrarg11.navigation.account.domain

import com.pereyrarg11.navigation.account.domain.signin.User
import com.pereyrarg11.navigation.core.domain.util.AppResult
import com.pereyrarg11.navigation.core.domain.util.DataError
import com.pereyrarg11.navigation.core.domain.util.EmptyAppResult

interface AuthRepository {

    suspend fun registerUser(
        name: String,
        lastName: String,
        phoneNumber: String,
        email: String,
    ): AppResult<User, DataError.Network>

    suspend fun getTokenByOtp(
        code: String,
        phoneNumber: String,
    ): EmptyAppResult<DataError.Network>

    suspend fun getTokenByPhoneNumber(
        phoneNumber: String,
    ): EmptyAppResult<DataError.Network>

    suspend fun sendSms(
        phoneNumber: String,
    ): EmptyAppResult<DataError.Network>

    suspend fun getUser(): AppResult<User, DataError.Network>

    suspend fun logout()

    suspend fun delete(): EmptyAppResult<DataError.Network>
}
