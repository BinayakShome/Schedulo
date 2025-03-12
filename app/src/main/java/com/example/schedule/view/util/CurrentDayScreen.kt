package com.example.schedule.view.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.schedule.view.component.NoInternet
import com.example.schedule.view.component.BottomNavBar.BottomNavBar
import com.example.schedule.view.component.CurrentClassCard
import com.example.schedule.vm.CurrentDayViewModel
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentDayScreen(
    navController: NavController,
    viewModel: CurrentDayViewModel
) {
    val context = LocalContext.current
    val showNoInternet by viewModel.showNoInternet.collectAsState()

    // Check internet availability on screen load
    LaunchedEffect(Unit) {
        viewModel.checkInternetAvailability(context)
    }

    val user = FirebaseAuth.getInstance().currentUser
    val userName = user?.displayName
        ?.substringBefore('_')
        ?.substringBefore(' ')
        ?: "User"

    // Get the current day dynamically
    val currentDay = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Hello, $userName \uD83D\uDC4B",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { navController.navigate("ProfileScreen") }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.DarkGray
                )
            )
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(paddingValues)
        ) {
            if (showNoInternet) {
                item {
                NoInternet()}
            } else {
                item {
                    Text(
                        text = "📅 $currentDay's Classes",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Sample class cards
                    CurrentClassCard(
                        subject = "Data Structures & Algorithms",
                        classFrom = "10:00 AM",
                        classTo = "11:30 AM",
                        roomNo = "405",
                        campusNo = "6"
                    )

                    CurrentClassCard(
                        subject = "Operating Systems",
                        classFrom = "12:00 PM",
                        classTo = "1:30 PM",
                        roomNo = "310",
                        campusNo = "6"
                    )

                    CurrentClassCard(
                        subject = "Database Management",
                        classFrom = "3:00 PM",
                        classTo = "4:30 PM",
                        roomNo = "502",
                        campusNo = "3"
                    )
                    CurrentClassCard(
                        subject = "Data Structures & Algorithms",
                        classFrom = "10:00 AM",
                        classTo = "11:30 AM",
                        roomNo = "405",
                        campusNo = "6"
                    )

                    CurrentClassCard(
                        subject = "Operating Systems",
                        classFrom = "12:00 PM",
                        classTo = "1:30 PM",
                        roomNo = "310",
                        campusNo = "6"
                    )

                    CurrentClassCard(
                        subject = "Database Management",
                        classFrom = "3:00 PM",
                        classTo = "4:30 PM",
                        roomNo = "502",
                        campusNo = "3"
                    )
                }
            }
        }
    }
}
