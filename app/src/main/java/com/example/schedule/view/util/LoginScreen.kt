package com.example.schedule.view.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.schedule.R
import com.example.schedule.data.model.SignInResult
import com.example.schedule.view.component.LoginButton

@Composable
fun LoginScreen(
    state: SignInResult?,
    onSignInClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Lottie Animation
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login_animation))
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Welcome Text
        Text(
            text = "Welcome to Schedulo",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        val context = LocalContext.current
//        LaunchedEffect(state) {
//            state.errorMessage?.let { error ->
//                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
//            }
//        }

        // Google Sign-In Button with Click Handler
        LoginButton(
            iconOnly = false,
            onClick = onSignInClick
        )
    }
}
