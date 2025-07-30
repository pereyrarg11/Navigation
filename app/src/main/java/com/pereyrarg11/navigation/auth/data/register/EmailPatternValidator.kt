package com.pereyrarg11.navigation.auth.data.register

import android.util.Patterns
import com.pereyrarg11.navigation.auth.domain.register.PatternValidator

object EmailPatternValidator : PatternValidator {
    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}
