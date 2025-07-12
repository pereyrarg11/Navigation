package com.pereyrarg11.navigation.auth.presentation.register

import androidx.compose.foundation.text.input.TextFieldState
import com.pereyrarg11.navigation.core.presentation.tools.UiText

data class RegisterState(
    val name: TextFieldState = TextFieldState(),
    val isValidName: Boolean = true,
    val nameError: UiText? = null,
    val lastName: TextFieldState = TextFieldState(),
    val isValidLastName: Boolean = true,
    val lastNameError: UiText? = null,
    val email: TextFieldState = TextFieldState(),
    val isValidEmail: Boolean = true,
    val emailError: UiText? = null,
    val phoneNumber: TextFieldState = TextFieldState(),
    val isValidPhoneNumber: Boolean = true,
    val phoneNumberError: UiText? = null,
    val isSubmitting: Boolean = false,
    val isBadRequest: Boolean = false,
    val badRequestMessage: UiText? = null,
)
