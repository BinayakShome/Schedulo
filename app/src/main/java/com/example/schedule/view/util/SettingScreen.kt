package com.example.schedule.view.util

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.schedule.data.model.UserInfo.UserData
import com.example.schedule.view.component.BottomSignature
import com.example.schedule.view.component.CustomButton
import com.example.schedule.view.component.DropDownMenu
import com.example.schedule.vm.SettingScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    settingScreenViewModel: SettingScreenViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("binayakshome3@gmail.com") }  // Assume email is constant
    var name by remember { mutableStateOf("Binayak Shome") }

    val userState = remember { mutableStateOf<UserData?>(null) }

    LaunchedEffect(email) {
        settingScreenViewModel.getUser(email) { userData ->
            userState.value = userData  // Update state inside callback
        }
    }
    val user = userState.value

    // State variables to store user input
    var year by remember { mutableStateOf(user?.year ?: "Select your Year") }
    var branch by remember { mutableStateOf(user?.branch ?: "Select your Branch") }
    var section by remember { mutableStateOf(user?.section ?: "Select your Section") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.DarkGray)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.DarkGray)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(name, color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(8.dp))
                Text(email, color = Color.White, fontSize = 16.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(24.dp))

                Text("Year", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                DropDownMenu(
                    text = "Year",
                    options = listOf("1st Year", "2nd Year", "3rd Year", "4th Year"),
                    selectedOption = year,
                    onOptionSelected = { year = it }
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text("Branch", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                DropDownMenu(
                    text = "Branch",
                    options = listOf("Computer Science", "Information Technology", "Computer Science and System Engineering"),
                    selectedOption = branch,
                    onOptionSelected = { branch = it }
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text("Section", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                DropDownMenu(
                    text = "Section",
                    options = listOf("CS-1", "CS-35", "CS-46"),
                    selectedOption = section,
                    onOptionSelected = { section = it }
                )

                Spacer(modifier = Modifier.height(64.dp))
                CustomButton(
                    onLogOutClick = {
                        val newUser = UserData(email = email, year = year, branch = branch, section = section)
                        settingScreenViewModel.insertUser(newUser)  // Save to Room DB
                        Toast.makeText(context, "Profile Saved!", Toast.LENGTH_SHORT).show()
                    },
                    text = "Save\uD83D\uDC4B",
                    btncolor = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF87CEEB), Color(0xFF00008B), Color(0xFF4B0082))
                    ),
                    textcolor = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            BottomSignature()
        }
    }
}
