package com.example.schedule.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.schedule.view.util.LoginScreen
import com.example.schedule.view.util.SetProfile
import com.example.schedule.vm.LoginViewModel
import com.example.schedule.vm.SettingScreenViewModel

fun NavGraphBuilder.loginNavGraph(
    navController: NavController,
    loginViewModel: LoginViewModel,
    settingScreenViewModel: SettingScreenViewModel
) {
    navigation(
        startDestination = Screen.LoginScreen.route,
        route = "login_graph"
    ) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                state = loginViewModel.signInResult,
                onSignInSuccess = {
                    navController.navigate("SetProfile") {
                        popUpTo("login_graph") { inclusive = true }
                    }
                },
                loginViewModel = loginViewModel
            )
        }

        composable(Screen.SetProfile.route) {
            SetProfile(
                navController = navController,
                settingScreenViewModel = settingScreenViewModel
            )
        }
    }
}