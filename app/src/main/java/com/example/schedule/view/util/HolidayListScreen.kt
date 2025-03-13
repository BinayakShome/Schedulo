package com.example.schedule.view.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.schedule.view.component.BottomSignature
import com.example.schedule.view.component.BottomNavBar.BottomNavBar
import com.example.schedule.view.component.HolidayItem
import com.example.schedule.view.component.NoInternet
import com.example.schedule.vm.HolidayViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HolidayListScreen(
    navController: NavController,
    holidayViewModel: HolidayViewModel = viewModel()
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }

    val holidayList by holidayViewModel.holidayList.collectAsState()
    val filteredList = holidayList.filter {
        it.EVENT.contains(searchQuery, ignoreCase = true)
    }
    val showNoInternet by holidayViewModel.showNoInternet.collectAsState()
    val isLoading by holidayViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        holidayViewModel.fetchHolidays(context)
    }
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
                ),
                shape = MaterialTheme.shapes.large
            )
            when {
                isLoading -> CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp),
                    color = Color.White
                )
                showNoInternet -> NoInternet()

                filteredList.isNotEmpty() -> LazyColumn {
                    items(filteredList) { holiday ->
                        HolidayItem(holiday)
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        BottomSignature() }
                }
                else -> Text(
                    text = "Oh, no such holiday found? Must be some mythical holiday you’re searching for, huh? \uD83E\uDD14",
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}