package com.pereyrarg11.navigation.auth.presentation.register

import com.pereyrarg11.navigation.R
import com.pereyrarg11.navigation.auth.domain.register.RegisterError
import com.pereyrarg11.navigation.auth.domain.register.ValidateEmailUseCase
import com.pereyrarg11.navigation.auth.domain.register.ValidateLastNameUseCase
import com.pereyrarg11.navigation.auth.domain.register.ValidateNameUseCase
import com.pereyrarg11.navigation.auth.domain.register.ValidatePhoneNumberUseCase
import com.pereyrarg11.navigation.core.presentation.tools.UiText

fun RegisterError.toUiText(): UiText {
    return when (this) {
        RegisterError.Email.EMPTY -> UiText.StringResource(R.string.auth_error_email_empty)
        RegisterError.Email.PATTERN -> UiText.StringResource(R.string.auth_error_email_pattern)
        RegisterError.Email.TOO_LONG -> UiText.StringResource(
            id = R.string.auth_error_email_too_long,
            args = arrayOf(ValidateEmailUseCase.EMAIL_MAX_LENGTH)
        )

        RegisterError.LastName.EMPTY -> UiText.StringResource(R.string.auth_error_last_name_empty)
        RegisterError.LastName.PATTERN -> UiText.StringResource(R.string.auth_error_last_name_pattern)
        RegisterError.LastName.TOO_LONG -> UiText.StringResource(
            id = R.string.auth_error_last_name_too_long,
            args = arrayOf(ValidateLastNameUseCase.LAST_NAME_MAX_LENGTH)
        )

        RegisterError.Name.EMPTY -> UiText.StringResource(R.string.auth_error_name_empty)
        RegisterError.Name.PATTERN -> UiText.StringResource(R.string.auth_error_name_pattern)
        RegisterError.Name.TOO_LONG -> UiText.StringResource(
            id = R.string.auth_error_name_too_long,
            args = arrayOf(ValidateNameUseCase.NAME_MAX_LENGTH)
        )

        RegisterError.PhoneNumber.EMPTY -> UiText.StringResource(R.string.auth_error_phone_number_empty)
        RegisterError.PhoneNumber.DIGITS -> UiText.StringResource(R.string.auth_error_phone_number_digits)
        RegisterError.PhoneNumber.LENGTH -> UiText.StringResource(
            id = R.string.auth_error_phone_number_length,
            args = arrayOf(ValidatePhoneNumberUseCase.PHONE_NUMBER_LENGTH)
        )
    }
}
