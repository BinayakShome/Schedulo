package com.example.schedule.navigation

sealed class Screen(val route: String)
{
    object LoginScreen : Screen("Loginscreen")
    object CurrentDayScreen : Screen("CurrentDayScreen")
    object ProfileScreen : Screen("ProfileScreen")
    object WeekScreen : Screen("WeekScreen")
    object HolidayListScreen : Screen("HolidayListScreen")
    object SettingScreen : Screen("SettingScreen")
    object SetProfile : Screen("SetProfile")
}