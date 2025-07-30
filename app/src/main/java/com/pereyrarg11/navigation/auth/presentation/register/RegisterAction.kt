package com.pereyrarg11.navigation.auth.presentation.register

sealed interface RegisterAction {
    data object OnClickSubmit : RegisterAction
    data object OnDismissError : RegisterAction
}
