package com.pereyrarg11.navigation.account.presentation.signin

import com.pereyrarg11.navigation.R
import com.pereyrarg11.navigation.account.domain.AccountFormError
import com.pereyrarg11.navigation.account.domain.signin.ValidateEmailUseCase
import com.pereyrarg11.navigation.account.domain.signin.ValidateLastNameUseCase
import com.pereyrarg11.navigation.account.domain.signin.ValidateNameUseCase
import com.pereyrarg11.navigation.account.domain.signin.ValidatePhoneNumberUseCase
import com.pereyrarg11.navigation.account.domain.verification.ValidateOtpUseCase
import com.pereyrarg11.navigation.core.presentation.tools.UiText

fun AccountFormError.toUiText(): UiText {
    return when (this) {
        AccountFormError.Email.EMPTY -> UiText.StringResource(R.string.account_error_email_empty)
        AccountFormError.Email.PATTERN -> UiText.StringResource(R.string.account_error_email_pattern)
        AccountFormError.Email.TOO_LONG -> UiText.StringResource(
            id = R.string.account_error_email_too_long,
            args = arrayOf(ValidateEmailUseCase.EMAIL_MAX_LENGTH)
        )

        AccountFormError.LastName.EMPTY -> UiText.StringResource(R.string.account_error_last_name_empty)
        AccountFormError.LastName.PATTERN -> UiText.StringResource(R.string.account_error_last_name_pattern)
        AccountFormError.LastName.TOO_LONG -> UiText.StringResource(
            id = R.string.account_error_last_name_too_long,
            args = arrayOf(ValidateLastNameUseCase.LAST_NAME_MAX_LENGTH)
        )

        AccountFormError.Name.EMPTY -> UiText.StringResource(R.string.account_error_name_empty)
        AccountFormError.Name.PATTERN -> UiText.StringResource(R.string.account_error_name_pattern)
        AccountFormError.Name.TOO_LONG -> UiText.StringResource(
            id = R.string.account_error_name_too_long,
            args = arrayOf(ValidateNameUseCase.NAME_MAX_LENGTH)
        )

        AccountFormError.PhoneNumber.EMPTY -> UiText.StringResource(R.string.account_error_phone_number_empty)
        AccountFormError.PhoneNumber.DIGITS -> UiText.StringResource(R.string.account_error_phone_number_digits)
        AccountFormError.PhoneNumber.LENGTH -> UiText.StringResource(
            id = R.string.account_error_phone_number_length,
            args = arrayOf(ValidatePhoneNumberUseCase.PHONE_NUMBER_LENGTH)
        )

        AccountFormError.OtpCode.LENGTH -> UiText.StringResource(
            id = R.string.account_error_otp_length,
            args = arrayOf(ValidateOtpUseCase.OTP_LENGTH)
        )
    }
}
