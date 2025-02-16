package com.example.schedule.view.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.schedule.view.component.BottomNavBar.BottomNavBar

@Composable
fun HolidayListScreen(
    navController: NavController
)
{
    Scaffold (
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ){paddingValues ->
        Column (modifier = Modifier.padding(paddingValues).background(Color.DarkGray)){  }
    }
}