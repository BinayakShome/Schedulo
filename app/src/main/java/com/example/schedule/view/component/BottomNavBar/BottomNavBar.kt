package com.example.schedule.view.component.BottomNavBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.schedule.navigation.Screen

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        Screen.CurrentDayScreen to Pair(Icons.Default.Home, "Today"),
        Screen.WeekScreen to Pair(Icons.Default.DateRange, "Week"),
        Screen.HolidayListScreen to Pair(Icons.Default.Person, "Holidays"),
    )

    NavigationBar(
        containerColor = Color.DarkGray,
        contentColor = Color.White
    ) {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination

        items.forEach { (screen, iconLabel) ->
            NavigationBarItem(
                icon = { Icon(iconLabel.first, contentDescription = iconLabel.second) },
                label = { Text(iconLabel.second) },
                selected = currentDestination?.route == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo("main") { inclusive = false }
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Cyan,
                    unselectedIconColor = Color.Gray,
                    indicatorColor = Color.Black
                )
            )
        }
    }
}