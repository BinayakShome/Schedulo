package com.example.schedule.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.schedule.view.util.LoginScreen
import com.example.schedule.vm.LoginViewModel

fun NavGraphBuilder.loginNavGraph(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    navigation(
        startDestination = Screen.LoginScreen.route,
        route = "login_graph"
    ) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                state = loginViewModel.signInResult,
                onSignInSuccess = {
                    navController.navigate("main") {
                        popUpTo("login_graph") { inclusive = true } // Prevent back navigation to login
                    }
                },
                loginViewModel = loginViewModel
            )
        }
    }
}

