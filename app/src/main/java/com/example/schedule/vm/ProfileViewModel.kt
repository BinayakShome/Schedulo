package com.example.schedule.vm

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.schedule.data.model.SignInResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileViewModel : ViewModel() {

    var signInResult by mutableStateOf<SignInResult?>(null)
        private set

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private var oneTapClient: SignInClient? = null

    fun signOut(context: Context) {
        auth.signOut()
        oneTapClient?.signOut()?.addOnCompleteListener {
            Toast.makeText(context, "Logged out successfully! 👋", Toast.LENGTH_SHORT).show()
            signInResult = null
        }?.addOnFailureListener { e ->
            Log.e("SignOut", "Error during Google sign-out", e)
            Toast.makeText(context, "Sign-out failed. Try again!", Toast.LENGTH_SHORT).show()
        }
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}