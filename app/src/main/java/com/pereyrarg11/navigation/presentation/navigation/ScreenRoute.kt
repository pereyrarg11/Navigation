package com.pereyrarg11.navigation.presentation.navigation

sealed class ScreenRoute(val route: String) {
    data object Login : ScreenRoute("login")
    data object SignUp : ScreenRoute("signup")
    data object Otp : ScreenRoute("otp?phoneNumber={phoneNumber}") {
        fun createRoute(phoneNumber: String): String {
            return "otp?phoneNumber=$phoneNumber"
        }
    }

    data object Home : ScreenRoute("home")
    data object Stats : ScreenRoute("stats")
    data object Profile : ScreenRoute("profile")
}
