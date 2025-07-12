package com.pereyrarg11.navigation.auth.data

import com.pereyrarg11.navigation.auth.data.register.RegisterUserRequest
import com.pereyrarg11.navigation.auth.data.register.UserDto
import com.pereyrarg11.navigation.auth.data.register.toUser
import com.pereyrarg11.navigation.auth.domain.AuthRepository
import com.pereyrarg11.navigation.auth.domain.register.User
import com.pereyrarg11.navigation.core.data.networking.post
import com.pereyrarg11.navigation.core.domain.util.AppResult
import com.pereyrarg11.navigation.core.domain.util.DataError
import com.pereyrarg11.navigation.core.domain.util.map
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
) : AuthRepository {

    override suspend fun registerUser(
        name: String,
        lastName: String,
        phoneNumber: String,
        email: String,
    ): AppResult<User, DataError.Network> {
        val result = httpClient.post<RegisterUserRequest, UserDto>(
            route = "/user",
            body = RegisterUserRequest(
                name = name,
                lastName = lastName,
                phone = phoneNumber,
                email = email
            )
        )

        // handle custom errors here
        if (result is AppResult.Success && result.data.errorCode == ERROR_CODE_DUPLICATION) {
            return AppResult.Error(DataError.Network.CONFLICT)
        }

        return result.map { it.toUser() }
    }

    companion object {
        private const val ERROR_CODE_DUPLICATION = 211
    }
}
