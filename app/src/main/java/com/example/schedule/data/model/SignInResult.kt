package com.example.schedule.data.model

import com.google.firebase.auth.FirebaseAuth

data class SignInResult(
    val success: FirebaseAuth? = null,
    val error: String? = null
)