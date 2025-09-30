package com.pereyrarg11.navigation.account.presentation.profile

sealed interface ProfileAction {
    data object OnClickLogout : ProfileAction
    data object OnClickDeleteAccount : ProfileAction
}
