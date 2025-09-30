package com.pereyrarg11.navigation.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.pereyrarg11.navigation.account.presentation.login.LoginScreen
import com.pereyrarg11.navigation.account.presentation.profile.ProfileScreen
import com.pereyrarg11.navigation.account.presentation.signin.SigninScreen
import com.pereyrarg11.navigation.account.presentation.verification.VerificationScreen
import com.pereyrarg11.navigation.home.presentation.HomeScreen
import com.pereyrarg11.navigation.stats.presentation.StatsScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

// TODO: implement Navigation3 once is stable
// TODO: MainViewModel should be able to know when session has started/finished; remove sessionStarted/sessionFinished functions
@Composable
fun NavigationRoot(
    navHostController: NavHostController,
    isLoggedIn: Boolean,
    onSessionStarted: () -> Unit,
    onSessionFinished: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navHostController,
        startDestination = if (isLoggedIn) ScreenRoute.Home.route else GraphRoute.AUTH,
        modifier = modifier,
    ) {
        authGraph(navHostController, onSessionStarted, onSessionFinished)
        composable(ScreenRoute.Home.route) {
            HomeScreen()
        }
        composable(ScreenRoute.Stats.route) {
            StatsScreen()
        }
    }
}

private fun NavGraphBuilder.authGraph(
    navHostController: NavHostController,
    onSessionStarted: () -> Unit,
    onSessionFinished: () -> Unit,
) {
    navigation(
        startDestination = ScreenRoute.Login.route,
        route = GraphRoute.AUTH
    ) {
        composable(route = ScreenRoute.Login.route) {
            LoginScreen(
                // TODO: check if user has a favorite team; if not then redirect to TeamChooserScreen (once is available)
                onLoginSuccess = {
                    onSessionStarted()
                    navHostController.navigate(ScreenRoute.Home.route) {
                        popUpTo(GraphRoute.AUTH) {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable(route = ScreenRoute.SignUp.route) {
            SigninScreen(
                onSendOtp = { phoneNumber ->
                    navHostController.navigate(ScreenRoute.Otp.createRoute(phoneNumber)) {
                        popUpTo(ScreenRoute.SignUp.route) {
                            inclusive = true
                            saveState = true
                        }
                    }
                },
            )
        }
        composable(
            route = ScreenRoute.Otp.route,
            arguments = listOf(
                navArgument(name = NavigationArgument.PHONE_NUMBER) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            val phoneNumberArg = it.arguments?.getString(NavigationArgument.PHONE_NUMBER)

            VerificationScreen(
                viewModel = koinViewModel {
                    parametersOf(phoneNumberArg.orEmpty())
                },
            ) {
                onSessionStarted()
                navHostController.navigate(ScreenRoute.Home.route) {
                    popUpTo(ScreenRoute.Otp.route) {
                        inclusive = true
                    }
                }
            }
        }
        composable(ScreenRoute.Profile.route) {
            ProfileScreen(
                onSessionClosed = {
                    onSessionFinished()
                    navHostController.navigate(ScreenRoute.Login.route) {
                        popUpTo(GraphRoute.AUTH) {
                            inclusive = true
                        }
                    }
                },
                onAccountDeleted = {
                    onSessionFinished()
                    navHostController.navigate(ScreenRoute.SignUp.route) {
                        popUpTo(GraphRoute.AUTH) {
                            inclusive = true
                        }
                    }
                },
            )
        }
    }
}
