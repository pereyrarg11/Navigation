package com.pereyrarg11.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.pereyrarg11.navigation.core.presentation.designsystem.AppTheme
import com.pereyrarg11.navigation.presentation.navigation.BottomNavigationBar
import com.pereyrarg11.navigation.presentation.navigation.BottomNavigationItem
import com.pereyrarg11.navigation.presentation.navigation.NavigationRoot
import com.pereyrarg11.navigation.presentation.navigation.ScreenRoute
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.state.isCheckingAuth
            }
        }
        // bottom navigation config
        val homeNav = BottomNavigationItem(
            title = getString(R.string.app_label_home),
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false,
        )
        val statsNav = BottomNavigationItem(
            title = getString(R.string.app_label_stats),
            selectedIcon = Icons.Filled.AccountBox,
            unselectedIcon = Icons.Outlined.AccountBox,
            hasNews = false,
            badgeCount = 45,
        )
        val profileNav = BottomNavigationItem(
            title = getString(R.string.app_label_profile),
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            hasNews = true,
        )
        val bottomNavItems = listOf(
            homeNav,
            statsNav,
            profileNav
        )
        val homeNavIndex = bottomNavItems.indexOf(homeNav)
        val statsNavIndex = bottomNavItems.indexOf(statsNav)
        val profileNavIndex = bottomNavItems.indexOf(profileNav)

        setContent {
            var selectedItemIndex by rememberSaveable {
                mutableIntStateOf(homeNavIndex)
            }
            AppTheme {
                KoinAndroidContext {
                    val navController = rememberNavController()
                    if (!viewModel.state.isCheckingAuth) {
                        Scaffold(
                            modifier = Modifier
                                .fillMaxSize(),
                            bottomBar = {
                                if (viewModel.state.isLoggedIn) {
                                    BottomNavigationBar(
                                        menuItems = bottomNavItems,
                                        selectedIndex = selectedItemIndex,
                                    ) { selection ->
                                        selectedItemIndex = selection
                                        when (selection) {
                                            homeNavIndex -> navController.navigate(ScreenRoute.Home.route)
                                            statsNavIndex -> navController.navigate(ScreenRoute.Stats.route)
                                            profileNavIndex -> navController.navigate(ScreenRoute.Profile.route)
                                            else -> Unit
                                        }
                                    }
                                }
                            },
                        ) { innerPadding ->
                            NavigationRoot(
                                navHostController = navController,
                                isLoggedIn = viewModel.state.isLoggedIn,
                                onSessionStarted = {
                                    viewModel.onSessionUpdated(true)
                                },
                                onSessionFinished = {
                                    viewModel.onSessionUpdated(false)
                                },
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                                    .padding(innerPadding)
                                    .consumeWindowInsets(innerPadding),
                            )
                        }
                    }
                }
            }
        }
    }
}
