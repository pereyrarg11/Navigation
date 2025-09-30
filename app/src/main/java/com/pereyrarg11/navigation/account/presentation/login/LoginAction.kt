package com.pereyrarg11.navigation.account.presentation.login

sealed interface LoginAction {
    data object OnClickSubmit : LoginAction
}
