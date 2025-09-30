package com.pereyrarg11.navigation.account.data.signin

import com.pereyrarg11.navigation.account.domain.signin.PatternValidator

object NamePatternValidator : PatternValidator {
    override fun matches(value: String): Boolean {
        val regexPattern = "\\b([A-ZÀ-ÿ][a-z. ]+ *)+"
        val regex = Regex(regexPattern)
        return regex.matches(value)
    }
}
