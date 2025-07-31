package com.pereyrarg11.navigation.account.presentation.verification

import com.pereyrarg11.navigation.core.presentation.tools.UiText

sealed interface VerificationEvent {
    data object SuccessfulVerification : VerificationEvent
    data class ShowError(val errorMessage: UiText) : VerificationEvent
}
