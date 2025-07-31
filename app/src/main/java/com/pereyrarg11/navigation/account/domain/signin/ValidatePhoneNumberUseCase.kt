package com.pereyrarg11.navigation.account.domain.signin

import com.pereyrarg11.navigation.account.domain.AccountFormError

class ValidatePhoneNumberUseCase {
    operator fun invoke(phoneNumber: String): AccountFormError.PhoneNumber? {
        if (phoneNumber.isEmpty()) {
            return AccountFormError.PhoneNumber.EMPTY
        }

        if (phoneNumber.length != PHONE_NUMBER_LENGTH) {
            return AccountFormError.PhoneNumber.LENGTH
        }

        val hasNoDigit = phoneNumber.any { !it.isDigit() }
        if (hasNoDigit) {
            return AccountFormError.PhoneNumber.DIGITS
        }

        return null
    }

    companion object {
        const val PHONE_NUMBER_LENGTH = 10
        const val PHONE_NUMBER_PREFIX = "+52"
    }
}
