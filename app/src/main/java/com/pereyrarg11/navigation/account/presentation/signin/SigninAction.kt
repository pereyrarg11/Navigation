package com.pereyrarg11.navigation.account.presentation.signin

sealed interface SigninAction {
    data object OnClickSubmit : SigninAction
}
