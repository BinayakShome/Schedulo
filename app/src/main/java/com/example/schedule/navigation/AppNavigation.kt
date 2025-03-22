package com.example.schedule.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.schedule.vm.LoginViewModel
import com.example.schedule.vm.SettingScreenViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    val loginViewModel: LoginViewModel = viewModel()
    val settingScreenViewModel: SettingScreenViewModel = viewModel()
    val isUserLoggedIn by rememberUpdatedState(Firebase.auth.currentUser != null)
    val startDestination = if (isUserLoggedIn) "main" else "login_graph"

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        loginNavGraph(navController, loginViewModel = loginViewModel, settingScreenViewModel = settingScreenViewModel)
        mainNavGraph(navController)
    }
}
