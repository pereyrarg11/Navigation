package com.pereyrarg11.navigation.auth.domain.register

class ValidatePhoneNumberUseCase {
    operator fun invoke(phoneNumber: String): RegisterError.PhoneNumber? {
        if (phoneNumber.isEmpty()) {
            return RegisterError.PhoneNumber.EMPTY
        }

        if (phoneNumber.length != PHONE_NUMBER_LENGTH) {
            return RegisterError.PhoneNumber.LENGTH
        }

        val hasNoDigit = phoneNumber.any { !it.isDigit() }
        if (hasNoDigit) {
            return RegisterError.PhoneNumber.DIGITS
        }

        return null
    }

    companion object {
        const val PHONE_NUMBER_LENGTH = 10
    }
}
