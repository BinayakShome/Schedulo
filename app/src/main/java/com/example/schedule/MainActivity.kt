package com.example.schedule

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
//import com.example.schedule.repo.GoogleAuthUiClient
import com.example.schedule.ui.theme.ScheduleTheme
import com.example.schedule.view.util.LoginScreen
import com.example.schedule.vm.LoginViewModel
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    private lateinit var googleAuthViewModel: LoginViewModel
    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            googleAuthViewModel.handleSignInResult(result.data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()

        googleAuthViewModel = LoginViewModel().apply {
            initGoogleSignIn(this@MainActivity)
        }

        setContent {
            ScheduleTheme (){
                LoginScreen(
                    state = googleAuthViewModel.signInResult,
                    onSignInClick = {
                        googleAuthViewModel.startGoogleSignIn(signInLauncher)
                    }
                )
            }
        }
    }
}
