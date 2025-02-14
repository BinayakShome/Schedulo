package com.example.schedule.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.schedule.view.util.HomeScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavController) {
    navigation(
        startDestination = Screen.HomeScreen.route,
        route = "main"
    ) {
        // Home Screen
        composable(Screen.HomeScreen.route) {
            HomeScreen(
                navController = navController
            )
        }

//        // Participant Form Screen
//        composable(Screen.ParticipantForm.route) {
//            ParticipantFormScreen(navController = navController)
//        }

//        // Profile Screen with Logout
//        composable(Screen.Profile.route) {
//            ProfileScreen(
//                navController = navController,
//                onBackClick = { navController.popBackStack() },
//                onLogOutClick = {
//                    Firebase.auth.signOut()
//                    navController.navigate("login_graph") {
//                        popUpTo("main") { inclusive = true }
//                    }
//                }
//            )
//        }
    }
}


