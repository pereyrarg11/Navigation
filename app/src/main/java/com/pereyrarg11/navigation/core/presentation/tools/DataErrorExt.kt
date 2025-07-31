package com.pereyrarg11.navigation.core.presentation.tools

import com.pereyrarg11.navigation.R
import com.pereyrarg11.navigation.core.domain.util.DataError

fun DataError.toUiText(): UiText {
    return when (this) {
        DataError.Local.DISK_FULL -> UiText.StringResource(R.string.core_error_disk_full)
        DataError.Network.NO_INTERNET -> UiText.StringResource(R.string.core_error_no_internet)
        DataError.Network.OTP_NOT_ACTIVE -> UiText.StringResource(R.string.account_error_otp_not_active)
        DataError.Network.CONFLICT -> UiText.StringResource(R.string.account_error_user_duplicated)
        DataError.Network.USER_NOT_ACTIVE -> UiText.StringResource(R.string.account_error_user_not_active)
        else -> UiText.StringResource(R.string.core_error_unknown)
    }
}
