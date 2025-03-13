package com.example.schedule.view.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.schedule.view.component.BottomNavBar.BottomNavBar
import com.example.schedule.view.component.BottomSignature
import com.example.schedule.view.component.NoInternet
import com.example.schedule.view.component.WeekScheduleList
import com.example.schedule.view.component.WeeklySchedule
import com.example.schedule.vm.WeekViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekScreen(navController: NavController, viewModel: WeekViewModel) {
    val context = LocalContext.current
    val showNoInternet by viewModel.showNoInternet.collectAsState()
    val weekSchedule by viewModel.weekSchedule.collectAsState()

    val firebaseUser = FirebaseAuth.getInstance().currentUser
    val email = firebaseUser?.email ?: "null"

    LaunchedEffect(email) {
        viewModel.checkInternetAvailability(context)
        viewModel.fetchWeeklySchedule(email)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Weekly Overview 📅",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.DarkGray)
            )
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(it)
        ) {
            if (showNoInternet) {
                NoInternet()
            } else {
                WeekScheduleList(weekSchedule)
            }
        }
    }
}