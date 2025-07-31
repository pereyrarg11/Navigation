package com.pereyrarg11.navigation.account.domain.signin

import com.pereyrarg11.navigation.account.domain.AccountFormError

class ValidateNameUseCase(
    private val namePatternValidator: PatternValidator,
) {
    operator fun invoke(name: String): AccountFormError.Name? {
        if (name.isEmpty()) {
            return AccountFormError.Name.EMPTY
        }

        val isValidPattern = namePatternValidator.matches(name)
        if (!isValidPattern) {
            return AccountFormError.Name.PATTERN
        }

        if (name.length > NAME_MAX_LENGTH) {
            return AccountFormError.Name.TOO_LONG
        }

        return null
    }

    companion object {
        const val NAME_MAX_LENGTH = 25
    }
}
