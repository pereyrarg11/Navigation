package com.pereyrarg11.navigation.account.presentation.login

import androidx.compose.foundation.text.input.TextFieldState
import com.pereyrarg11.navigation.core.presentation.tools.UiText

data class LoginState(
    val phoneNumber: TextFieldState = TextFieldState(),
    val isValidPhoneNumber: Boolean = true,
    val phoneNumberError: UiText? = null,
    val isSubmitting: Boolean = false,
)
