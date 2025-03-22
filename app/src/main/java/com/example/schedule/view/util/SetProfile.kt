package com.example.schedule.view.util

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.schedule.data.model.UserInfo.UserData
import com.example.schedule.view.component.BottomSignature
import com.example.schedule.view.component.CustomButton
import com.example.schedule.view.component.DropDownMenu
import com.example.schedule.vm.SettingScreenViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SetProfile(
    settingScreenViewModel: SettingScreenViewModel,
    navController: NavController
)
{
    val context = LocalContext.current
    val firebaseUser = FirebaseAuth.getInstance().currentUser
    var email by remember { mutableStateOf(firebaseUser?.email ?: "Mr_Unknown.com ??") }
    var name by remember { mutableStateOf(firebaseUser?.displayName ?: "\uD83D\uDE02") }
    val userState = remember { mutableStateOf<UserData?>(null) }
    val sections by settingScreenViewModel.sections.collectAsState()
    val user = userState.value
    var year by remember { mutableStateOf(user?.year ?: "Select your Year") }
    var branch by remember { mutableStateOf(user?.branch ?: "Select your Branch") }
    var section by remember { mutableStateOf("Select your Section") }

    LaunchedEffect(email) {
        settingScreenViewModel.getUser(email) { userData ->
            userState.value = userData
        }
    }

    LaunchedEffect(year, branch) {
        if (year != "Select your Year" && branch != "Select your Branch") {
            settingScreenViewModel.fetchSectionsFromFirebase(year, branch)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(16.dp)
            .padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = name,
                color = Color(0xFFEE0979),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = email,
                color = Color.White,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
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
                //options = if(year.isNotEmpty()) year else listOf("List of branches"),
                options = listOf("CSE", "IT", "CSSE", "CSCE"),
                selectedOption = branch,
                onOptionSelected = { branch = it }
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text("Section", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            DropDownMenu(
                text = "Section",
                options = if (sections.isNotEmpty()) sections else listOf("Looking Out for your Section..."),
                selectedOption = section,
                onOptionSelected = { section = it }
            )
            Spacer(modifier = Modifier.height(64.dp))

            CustomButton(
                onLogOutClick = {
                    if (year == "Select your Year" || branch == "Select your Branch" || section == "Select your Section") {
                        Toast.makeText(context, "No time to all Data???", Toast.LENGTH_SHORT).show()
                    } else {
                        val newUser =
                            UserData(email = email, year = year, branch = branch, section = section)
                        settingScreenViewModel.insertUser(newUser)
                        Toast.makeText(
                            context,
                            "Thanks for sharing your Data\uD83D\uDC80",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.navigate("main") {
                            popUpTo(0) // Removes all previous destinations from the back stack
                            launchSingleTop =
                                true // Ensures that only a single instance of "main" exists
                        }
                    }
                },
                text = "Save\uD83D\uDC4B",
                btncolor = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF87CEEB), Color(0xFF00008B), Color(0xFF4B0082),Color(0xFFEE0979), Color(0xFFFF6A00))
                ),
                textcolor = Color.White
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        BottomSignature()
    }
}