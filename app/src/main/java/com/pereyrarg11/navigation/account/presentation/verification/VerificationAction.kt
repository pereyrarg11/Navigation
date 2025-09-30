package com.pereyrarg11.navigation.account.presentation.verification

sealed interface VerificationAction {
    data class OnEnterDigit(val digit: Int?, val index: Int) : VerificationAction
    data class OnChangeFieldFocused(val index: Int) : VerificationAction
    data object OnKeyboardBack : VerificationAction
    data object OnClickSubmit : VerificationAction
}
