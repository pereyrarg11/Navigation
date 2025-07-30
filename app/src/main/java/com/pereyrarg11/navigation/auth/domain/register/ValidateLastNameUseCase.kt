package com.pereyrarg11.navigation.auth.domain.register

class ValidateLastNameUseCase(
    private val lastNamePatternValidator: PatternValidator,
) {
    operator fun invoke(lastName: String): RegisterError.LastName? {
        if (lastName.isEmpty()) {
            return RegisterError.LastName.EMPTY
        }

        val isValidPattern = lastNamePatternValidator.matches(lastName)
        if (!isValidPattern) {
            return RegisterError.LastName.PATTERN
        }

        if (lastName.length > LAST_NAME_MAX_LENGTH) {
            return RegisterError.LastName.TOO_LONG
        }

        return null
    }

    companion object {
        const val LAST_NAME_MAX_LENGTH = 25
    }
}
