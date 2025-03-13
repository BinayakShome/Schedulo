package com.example.schedule.view.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.schedule.data.model.UserInfo.UserData
import com.example.schedule.view.component.NoInternet
import com.example.schedule.view.component.BottomNavBar.BottomNavBar
import com.example.schedule.view.component.CurrentClassCard
import com.example.schedule.vm.CurrentDayViewModel
import com.example.schedule.vm.SettingScreenViewModel
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

    // Get the current user's email
    val firebaseUser = FirebaseAuth.getInstance().currentUser
    val email = firebaseUser?.email ?: "null"

    // Fetch the user's schedule when the screen loads
    LaunchedEffect(email) {
        viewModel.fetchCurrentDaySchedule(email)
    }

    val classSchedule by viewModel.classSchedule.collectAsState()
    val currentDay = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())

    val user = FirebaseAuth.getInstance().currentUser
    val userName = user?.displayName
        ?.substringBefore('_')
        ?.substringBefore(' ')
        ?: "User"

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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.DarkGray)
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
                item { NoInternet() }
            } else {
                item {
                    Text(
                        text = "📅 $currentDay's Schedule",
                        color = Color.Cyan,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp),
                        fontSize = 24.sp
                    )
                }
                if (classSchedule.isEmpty()) {
                    item {
                        Text(
                            text = "No classes scheduled today 😎",
                            color = Color.LightGray,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                        )
                    }
                } else {
                    items(classSchedule) { classItem ->
                        CurrentClassCard(
                            subject = classItem.subject,
                            classFrom = classItem.time.split("-")[0] + ":00",
                            classTo = classItem.time.split("-")[1] + ":00",
                            roomNo = classItem.room,
                            campusNo = classItem.campus
                        )
                    }
                }
            }
        }
    }
}
