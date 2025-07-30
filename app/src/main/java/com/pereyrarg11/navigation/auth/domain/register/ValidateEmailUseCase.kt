package com.pereyrarg11.navigation.auth.domain.register

class ValidateEmailUseCase(
    private val emailPatternValidator: PatternValidator,
) {
    operator fun invoke(email: String): RegisterError.Email? {
        if (email.isEmpty()) {
            return RegisterError.Email.EMPTY
        }

        val isValidPattern = emailPatternValidator.matches(email)
        if (!isValidPattern) {
            return RegisterError.Email.PATTERN
        }

        if (email.length > EMAIL_MAX_LENGTH) {
            return RegisterError.Email.TOO_LONG
        }

        return null
    }

    companion object {
        const val EMAIL_MAX_LENGTH = 50
    }
}
