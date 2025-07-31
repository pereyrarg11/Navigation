package com.pereyrarg11.navigation.account.domain.signin

import com.pereyrarg11.navigation.account.domain.AccountFormError

class ValidateEmailUseCase(
    private val emailPatternValidator: PatternValidator,
) {
    operator fun invoke(email: String): AccountFormError.Email? {
        if (email.isEmpty()) {
            return AccountFormError.Email.EMPTY
        }

        val isValidPattern = emailPatternValidator.matches(email)
        if (!isValidPattern) {
            return AccountFormError.Email.PATTERN
        }

        if (email.length > EMAIL_MAX_LENGTH) {
            return AccountFormError.Email.TOO_LONG
        }

        return null
    }

    companion object {
        const val EMAIL_MAX_LENGTH = 50
    }
}
