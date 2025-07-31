package com.pereyrarg11.navigation.account.presentation.signin

import com.pereyrarg11.navigation.core.presentation.tools.UiText

sealed interface SigninEvent {
    data class RegistrationSuccess(val phoneNumber: String) : SigninEvent
    data class ShowError(val errorMessage: UiText) : SigninEvent
}
