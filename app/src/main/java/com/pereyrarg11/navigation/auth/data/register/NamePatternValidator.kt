package com.pereyrarg11.navigation.auth.data.register

import com.pereyrarg11.navigation.auth.domain.register.PatternValidator

object NamePatternValidator : PatternValidator {
    override fun matches(value: String): Boolean {
        val regexPattern = "\\b([A-ZÀ-ÿ][a-z. ]+ *)+"
        val regex = Regex(regexPattern)
        return regex.matches(value)
    }
}
