package com.pereyrarg11.navigation.auth.presentation.register

sealed interface RegisterEvent {
    data object RegistrationSuccess : RegisterEvent
}
