package com.example.schedule.view.util

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.schedule.R
import com.example.schedule.view.component.BottomNavBar.BottomNavBar
import com.example.schedule.view.component.HolidayItem
import com.example.schedule.vm.HolidayViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HolidayListScreen(
    navController: NavController,
    holidayViewModel: HolidayViewModel = viewModel()
) {
    val context = LocalContext.current
    val isConnected = remember { mutableStateOf(holidayViewModel.isInternetAvailable(context)) }
    var searchQuery by remember { mutableStateOf("") }

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.other_screen_no_internet_animation)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    // Automatically fetch holidays when online
    LaunchedEffect(isConnected.value) {
        if (isConnected.value) {
            holidayViewModel.fetchHolidays()
        }
    }

    val holidayList by holidayViewModel.holidayList.collectAsState()
    val filteredList = holidayList.filter {
        it.EVENT.contains(searchQuery, ignoreCase = true)
    }
    val errorMessage by holidayViewModel.errorMessage.collectAsState()
    val isLoading by holidayViewModel.isLoading.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(paddingValues)
                .padding(top = 24.dp)
        ) {
            if (isConnected.value) {
                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    placeholder = {
                        Text(text = "Search holidays", color = Color.LightGray)
                    },
                    singleLine = true,
                    textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Cyan,
                        unfocusedBorderColor = Color.Cyan
                    )
                )

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 16.dp),
                        color = Color.White
                    )
                } else if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                } else if (filteredList.isNotEmpty()) {
                    LazyColumn {
                        items(filteredList) { holiday ->
                            HolidayItem(holiday)
                        }
                    }
                } else {
                    Text(
                        text = "Oh, no such holiday found? Must be some mythical holiday you’re searching for, huh? \uD83E\uDD14",
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                // Show No Internet Animation
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LottieAnimation(
                        composition = composition,
                        progress = { progress },
                        modifier = Modifier.size(400.dp)
                    )
                    Text(
                        text = "The internet got lost... didn’t drop a pin!",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

//@Composable
//fun HolidayItem(holiday: Holiday) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//            .background(Brush.verticalGradient(
//                colors = listOf(Color(0xFF00C6FF), Color(0xFF0072FF)) // Gradient background
//            ))
//            .clip(MaterialTheme.shapes.medium) // Rounded corners
////            .shadow(8.dp, shape = MaterialTheme.shapes.medium) // Elevated shadow
//    ) {
//            Column(modifier = Modifier.padding(20.dp)) {
//                Text(
//                    text = holiday.EVENT,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.White,
//                    fontSize = 22.sp, // Increased font size
//                    modifier = Modifier.padding(bottom = 4.dp) // Added spacing
//                )
//                Text(
//                    text = "Date: ${holiday.DATE}",
//                    color = Color(0xFFE0E0E0), // Lighter text color for secondary info
//                    fontSize = 16.sp
//                )
//                Text(
//                    text = "Day: ${holiday.DAYS}",
//                    color = Color(0xFFE0E0E0), // Lighter text color for secondary info
//                    fontSize = 16.sp
//                )
//            }
//        }
//    }
//
