package com.example.schedule.view.util

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.schedule.R
import com.example.schedule.data.model.UserInfo.UserData
import com.example.schedule.view.component.BottomSignature
import com.example.schedule.view.component.CustomButton
import com.example.schedule.view.component.LogoutConfirmationCard
import com.example.schedule.vm.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    onLogOutClick: () -> Unit,
    viewModel: ProfileViewModel
) {
    val context = LocalContext.current
    val isConnected = remember { mutableStateOf(viewModel.isInternetAvailable(context)) }
    val showLogoutDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isConnected.value = viewModel.isInternetAvailable(context)
    }

    var savedUser by remember { mutableStateOf<UserData?>(null) }
    val firebaseUser = FirebaseAuth.getInstance().currentUser
    val email = firebaseUser?.email ?: "null"

    LaunchedEffect(email) {
        viewModel.getUser(email) { user ->
            savedUser = user
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.DarkGray
                ),
                actions = {
                    IconButton(onClick = { navController.navigate("SettingScreen") }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Settings",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isConnected.value) {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.profile_screen_no_internet_animation))
                val progress by animateLottieCompositionAsState(
                    composition = composition,
                    iterations = LottieConstants.IterateForever
                )
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier.size(250.dp)
                )
                Text(
                    text = "Aliens must have stolen your Wi-Fi! 👽",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            } else {
                val photoUrl = firebaseUser?.photoUrl
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (photoUrl != null) {
                        AsyncImage(
                            model = photoUrl,
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.Cyan, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .background(Color.Gray),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Default Profile",
                                tint = Color.White,
                                modifier = Modifier.size(120.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = firebaseUser?.displayName ?: "Naam kya rakha, bhai? 😄",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = firebaseUser?.email ?: "Fraud lagte ho 😎",
                            color = Color.White,
                            fontSize = 16.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
//                            Spacer(modifier = Modifier.height(6.dp))
//                            Text(
//                                text = firebaseUser?.phoneNumber?.takeIf { it.isNotBlank() } ?: "Phone: Tumhe Kyun Batau",
//                                color = Color.White,
//                                fontSize = 14.sp,
//                                overflow = TextOverflow.Ellipsis
//                            )
                    }
                }


                savedUser?.let { user ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(Color(0xFF262626)),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Data so rare, even Google wants it \uD83E\uDD21",
                                fontSize = 18.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Year: ${user.year}",
                                color = Color.LightGray,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "Branch: ${user.branch}",
                                color = Color.LightGray,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "Section: ${user.section}",
                                color = Color.LightGray,
                                fontSize = 16.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))

                CustomButton(
                    onLogOutClick = { showLogoutDialog.value = true },
                    text = "Log out 👋",
                    btncolor = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFEE0979), Color(0xFFFF6A00))
                    ),
                    textcolor = Color.White
                )
            }

            Spacer(modifier = Modifier.height(64.dp))
            Text("Coded with ❤\uFE0F and ☕")
            BottomSignature()
        }
    }

    if (showLogoutDialog.value) {
        LogoutConfirmationCard(
            onConfirm = {
                showLogoutDialog.value = false
                onLogOutClick()
            },
            onDismiss = { showLogoutDialog.value = false }
        )
    }
}