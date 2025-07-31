package com.pereyrarg11.navigation.account.data.signin

import android.util.Patterns
import com.pereyrarg11.navigation.account.domain.signin.PatternValidator

object EmailPatternValidator : PatternValidator {
    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}
