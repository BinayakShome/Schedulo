package com.example.schedule.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.schedule.view.util.CurrentDayScreen
import com.example.schedule.view.util.HolidayListScreen
import com.example.schedule.view.util.ProfileScreen
import com.example.schedule.view.util.WeekScreen
import com.example.schedule.vm.CurrentDayViewModel
import com.example.schedule.vm.ProfileViewModel
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
            val currentDayViewModel: CurrentDayViewModel = viewModel()

            CurrentDayScreen(
                navController = navController,
                viewModel = currentDayViewModel
            )
        }

//        // Participant Form Screen
//        composable(Screen.ParticipantForm.route) {
//            ParticipantFormScreen(navController = navController)
//        }

        // Profile Screen with Logout
        composable(Screen.ProfileScreen.route) {
            val profileViewModel: ProfileViewModel = viewModel()

            ProfileScreen(
                navController = navController,
                onLogOutClick = {
                    Firebase.auth.signOut()
                    navController.navigate("login_graph") {
                        popUpTo("main") { inclusive = true }
                    }
                },
                viewModel = profileViewModel
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


