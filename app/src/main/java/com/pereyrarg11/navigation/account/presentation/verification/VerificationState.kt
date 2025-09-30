package com.pereyrarg11.navigation.account.presentation.verification

import com.pereyrarg11.navigation.account.domain.verification.ValidateOtpUseCase
import com.pereyrarg11.navigation.core.presentation.tools.UiText

data class VerificationState(
    val code: List<Int?> = (1..ValidateOtpUseCase.OTP_LENGTH).map { null },
    val isValidCode: Boolean? = null,
    val codeErrorMessage: UiText? = null,
    val focusedIndex: Int? = null,
    val isSubmitting: Boolean = false,
)
