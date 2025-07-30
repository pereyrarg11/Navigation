package com.pereyrarg11.navigation.auth.domain.register

class ValidateNameUseCase(
    private val namePatternValidator: PatternValidator,
) {
    operator fun invoke(name: String): RegisterError.Name? {
        if (name.isEmpty()) {
            return RegisterError.Name.EMPTY
        }

        val isValidPattern = namePatternValidator.matches(name)
        if (!isValidPattern) {
            return RegisterError.Name.PATTERN
        }

        if (name.length > NAME_MAX_LENGTH) {
            return RegisterError.Name.TOO_LONG
        }

        return null
    }

    companion object {
        const val NAME_MAX_LENGTH = 25
    }
}
