package com.pereyrarg11.navigation.account.domain.verification

import com.pereyrarg11.navigation.account.domain.AccountFormError

class ValidateOtpUseCase {

    operator fun invoke(code: List<Int?>): AccountFormError.OtpCode? {
        val digits = code.filterNotNull()
        if (digits.size != OTP_LENGTH) {
            return AccountFormError.OtpCode.LENGTH
        }
        return null
    }

    companion object {
        const val OTP_LENGTH = 4
    }
}
