package com.pereyrarg11.navigation.account.presentation.login

import com.pereyrarg11.navigation.core.presentation.tools.UiText

sealed interface LoginEvent {
    data object LoginSuccess : LoginEvent
    data class ShowError(val errorMessage: UiText) : LoginEvent
}
