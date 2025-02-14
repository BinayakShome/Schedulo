package com.example.schedule.navigation

sealed class Screen(val route: String)
{
    object LoginScreen : Screen("Loginscreen")
    object CurrentDayScreen : Screen("CurrentDayScreen")
    object ProfileScreen : Screen("ProfileScreen")
}