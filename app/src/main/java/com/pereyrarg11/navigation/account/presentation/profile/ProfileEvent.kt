package com.pereyrarg11.navigation.account.presentation.profile

import com.pereyrarg11.navigation.core.presentation.tools.UiText

sealed interface ProfileEvent {
    data object AccountDeleted : ProfileEvent
    data object SessionClosed : ProfileEvent
    data class ShowError(val errorMessage: UiText) : ProfileEvent
}
