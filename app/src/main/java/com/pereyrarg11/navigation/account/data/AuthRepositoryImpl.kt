package com.pereyrarg11.navigation.account.data

import com.pereyrarg11.navigation.account.data.signin.DeleteAccountDto
import com.pereyrarg11.navigation.account.data.signin.RegisterUserRequest
import com.pereyrarg11.navigation.account.data.signin.UserDto
import com.pereyrarg11.navigation.account.data.signin.toUser
import com.pereyrarg11.navigation.account.data.sms.SendSmsRequest
import com.pereyrarg11.navigation.account.data.sms.SmsDto
import com.pereyrarg11.navigation.account.data.token.GetTokenRequest
import com.pereyrarg11.navigation.account.data.token.TokenDto
import com.pereyrarg11.navigation.account.domain.AuthRepository
import com.pereyrarg11.navigation.account.domain.signin.User
import com.pereyrarg11.navigation.core.data.SecretValues
import com.pereyrarg11.navigation.core.data.networking.delete
import com.pereyrarg11.navigation.core.data.networking.get
import com.pereyrarg11.navigation.core.data.networking.post
import com.pereyrarg11.navigation.core.domain.session.AuthInfo
import com.pereyrarg11.navigation.core.domain.session.SessionStorage
import com.pereyrarg11.navigation.core.domain.util.AppResult
import com.pereyrarg11.navigation.core.domain.util.DataError
import com.pereyrarg11.navigation.core.domain.util.EmptyAppResult
import com.pereyrarg11.navigation.core.domain.util.asEmptyAppResult
import com.pereyrarg11.navigation.core.domain.util.map
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val userHttpClient: HttpClient,
    private val secrets: SecretValues,
    private val sessionStorage: SessionStorage,
) : AuthRepository {

    override suspend fun registerUser(
        name: String,
        lastName: String,
        phoneNumber: String,
        email: String,
    ): AppResult<User, DataError.Network> {
        val result = httpClient.post<RegisterUserRequest, UserDto>(
            baseUrl = secrets.authUrl,
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

    override suspend fun getTokenByOtp(
        code: String,
        phoneNumber: String,
    ): EmptyAppResult<DataError.Network> {
        val result = httpClient.post<GetTokenRequest, TokenDto>(
            baseUrl = secrets.authUrl,
            route = "/oauth2/token",
            body = GetTokenRequest(
                grantType = GRANT_TYPE_PHONE_NUMBER,
                phoneNumber = phoneNumber,
                code = code
            )
        )
        if (result is AppResult.Success) {
            if (result.data.errorCode != null) {
                return transformErrorCode(result.data.errorCode)
            }
            onGivenToken(result.data)
        }
        return result.asEmptyAppResult()
    }

    override suspend fun getTokenByPhoneNumber(phoneNumber: String): EmptyAppResult<DataError.Network> {
        val result = httpClient.post<GetTokenRequest, TokenDto>(
            baseUrl = secrets.authUrl,
            route = "/oauth2/token",
            body = GetTokenRequest(
                grantType = GRANT_TYPE_VALIDATE_USER,
                phoneNumber = phoneNumber,
            )
        )
        if (result is AppResult.Success) {
            if (result.data.errorCode != null) {
                return transformErrorCode(result.data.errorCode)
            }
            onGivenToken(result.data)
        }
        return result.asEmptyAppResult()
    }

    override suspend fun sendSms(phoneNumber: String): EmptyAppResult<DataError.Network> {
        val body = SendSmsRequest(
            appType = secrets.authAppType,
            deviceType = secrets.authDeviceType,
            phoneNumber = phoneNumber,
        )
        val result = httpClient.post<SendSmsRequest, SmsDto>(
            baseUrl = secrets.authUrl,
            route = "/sms_request/create",
            body = body,
        )
        if (result is AppResult.Success && result.data.code != null) {
            // check whether code is an error, otherwise continue
            when (result.data.code) {
                ERROR_CODE_USER_NOT_FOUND -> return AppResult.Error(DataError.Network.USER_NOT_FOUND)
                else -> Unit
            }
        }
        return result.asEmptyAppResult()
    }

    override suspend fun getUser(): AppResult<User, DataError.Network> {
        val result = userHttpClient.get<UserDto>(
            baseUrl = secrets.authUrl,
            route = "/me",
        )
        // TODO: validate error codes here!
        return result.map { userDto -> userDto.toUser() }
    }

    override suspend fun logout() {
        // TODO: remove user from local storage
        sessionStorage.set(null)
    }

    override suspend fun delete(): EmptyAppResult<DataError.Network> {
        val result = userHttpClient.delete<DeleteAccountDto>(
            baseUrl = secrets.authUrl,
            route = "/me",
        )
        // TODO: remove user from local storage
        sessionStorage.set(null)
        return result.asEmptyAppResult()
    }

    private fun transformErrorCode(errorCode: Int): AppResult.Error<DataError.Network> {
        val error = when (errorCode) {
            ERROR_CODE_OTP_NOT_ACTIVE -> DataError.Network.OTP_NOT_ACTIVE
            ERROR_CODE_USER_NOT_ACTIVE -> DataError.Network.USER_NOT_ACTIVE
            ERROR_CODE_USER_NOT_VALID -> DataError.Network.USER_NOT_FOUND
            else -> DataError.Network.UNKNOWN
        }
        return AppResult.Error(error)
    }

    private suspend fun onGivenToken(tokenDto: TokenDto) {
        if (!tokenDto.accessToken.isNullOrEmpty() && !tokenDto.refreshToken.isNullOrEmpty()) {
            val authInfo = AuthInfo(
                accessToken = tokenDto.accessToken,
                refreshToken = tokenDto.refreshToken,
            )
            sessionStorage.set(authInfo)
        }
    }

    companion object {
        private const val ERROR_CODE_DUPLICATION = 211
        private const val ERROR_CODE_OTP_NOT_ACTIVE = 8
        private const val ERROR_CODE_USER_NOT_ACTIVE = 11
        private const val ERROR_CODE_USER_NOT_FOUND = 404
        private const val ERROR_CODE_USER_NOT_VALID = 10
        private const val GRANT_TYPE_PHONE_NUMBER = "phone_number"
        private const val GRANT_TYPE_VALIDATE_USER = "validate_user"
    }
}
