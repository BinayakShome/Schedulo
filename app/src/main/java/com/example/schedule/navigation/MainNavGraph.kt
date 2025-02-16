package com.example.schedule.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.schedule.view.util.CurrentDayScreen
import com.example.schedule.view.util.HolidayListScreen
import com.example.schedule.view.util.ProfileScreen
import com.example.schedule.view.util.WeekScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

fun NavGraphBuilder.mainNavGraph(
    navController: NavController) {
    navigation(
        startDestination = Screen.CurrentDayScreen.route,
        route = "main"
    ) {
        // Home Screen
        composable(Screen.CurrentDayScreen.route) {
            CurrentDayScreen(
                navController = navController
            )
        }

//        // Participant Form Screen
//        composable(Screen.ParticipantForm.route) {
//            ParticipantFormScreen(navController = navController)
//        }

        // Profile Screen with Logout
        composable(Screen.ProfileScreen.route) {
            ProfileScreen(
                navController = navController,
                //onBackClick = { navController.popBackStack() },
                onLogOutClick = {
                    Firebase.auth.signOut()
                    navController.navigate("login_graph") {
                        popUpTo("main") { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.WeekScreen.route) {
            WeekScreen(navController = navController)
        }

        composable(Screen.HolidayListScreen.route) {
            HolidayListScreen(navController = navController)
        }
    }
}


