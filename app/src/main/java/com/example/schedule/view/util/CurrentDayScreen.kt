package com.example.schedule.view.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.schedule.R
import com.example.schedule.view.component.BottomNavBar.BottomNavBar
import com.example.schedule.view.component.NoInternet
import com.example.schedule.vm.CurrentDayViewModel
import com.example.schedule.vm.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(paddingValues)
        ) {
            if (showNoInternet) {
                NoInternet()
            } else {
                Text(
                    text = "Welcome to Current Day Screen",
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
