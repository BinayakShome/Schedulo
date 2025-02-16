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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.schedule.view.component.BottomNavBar.BottomNavBar
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentDayScreen(navController: NavController) {
    val user = FirebaseAuth.getInstance().currentUser
    val userName = user?.displayName
        ?.substringBefore('_')  // Take only up to the first underscore
        ?.substringBefore(' ')  // Take only up to the first space
        ?: "User"


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Hello, " + userName + " \uD83D\uDC4B",
                        color = Color.White, fontWeight = FontWeight.Bold)
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
        Column(modifier = Modifier.fillMaxSize().background(Color.DarkGray)
            .padding(paddingValues)) {
                Text(
                    text = "Welcome to Current Day Screen",
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )

        }
    }
}
