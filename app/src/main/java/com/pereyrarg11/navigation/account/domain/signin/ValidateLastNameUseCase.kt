package com.pereyrarg11.navigation.account.domain.signin

import com.pereyrarg11.navigation.account.domain.AccountFormError

class ValidateLastNameUseCase(
    private val lastNamePatternValidator: PatternValidator,
) {
    operator fun invoke(lastName: String): AccountFormError.LastName? {
        if (lastName.isEmpty()) {
            return AccountFormError.LastName.EMPTY
        }

        val isValidPattern = lastNamePatternValidator.matches(lastName)
        if (!isValidPattern) {
            return AccountFormError.LastName.PATTERN
        }

        if (lastName.length > LAST_NAME_MAX_LENGTH) {
            return AccountFormError.LastName.TOO_LONG
        }

        return null
    }

    companion object {
        const val LAST_NAME_MAX_LENGTH = 25
    }
}
