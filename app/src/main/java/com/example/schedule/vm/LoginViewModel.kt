package com.example.schedule.vm

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.schedule.R
import com.example.schedule.data.model.SignInResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore


class LoginViewModel : ViewModel() {
    var signInResult by mutableStateOf<SignInResult?>(null)
        private set

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private var oneTapClient: SignInClient? = null
    private var signInRequest: BeginSignInRequest? = null

    @SuppressLint("ServiceCast")
    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun initGoogleSignIn(context: Activity) {
        if (oneTapClient == null) {
            oneTapClient = Identity.getSignInClient(context)
            signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(context.getString(R.string.web_client_id))
                        .setFilterByAuthorizedAccounts(false)
                        .build()
                ).build()
        }
    }

    fun startGoogleSignIn(context: Context, launcher: ActivityResultLauncher<IntentSenderRequest>) {
        if (!isInternetAvailable(context)) {
            Toast.makeText(context, "Internet on vacation?\uD83D\uDE0F\nTry again later!", Toast.LENGTH_SHORT).show()
            return
        }

        val client = oneTapClient ?: run {
            signInResult = SignInResult(error = "Google Sign-In is not initialized.")
            return
        }

        val request = signInRequest ?: run {
            signInResult = SignInResult(error = "Sign-In request is missing.")
            return
        }

        client.beginSignIn(request)
            .addOnSuccessListener { result ->
                val intentSenderRequest = IntentSenderRequest.Builder(result.pendingIntent).build()
                launcher.launch(intentSenderRequest)
            }
            .addOnFailureListener { e ->
                Log.e("SignIn", "Google Sign-In failed", e)
                signInResult = SignInResult(error = e.localizedMessage ?: "Google Sign-In failed")
            }
    }

    fun handleSignInResult(data: Intent?) {
        try {
            val credential = oneTapClient?.getSignInCredentialFromIntent(data)
            val idToken = credential?.googleIdToken

            if (idToken != null) {
                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            user?.let {
                                val userData = mapOf(
                                    "uid" to it.uid,
                                    "name" to it.displayName,
                                    "email" to it.email
                                )
                                firestore.collection("users").document(it.uid).set(userData)
                                    .addOnSuccessListener {
                                        Log.d("Firestore", "User added to Firestore")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("Firestore", "Error adding user", e)
                                    }
                            }
                            signInResult = SignInResult(success = auth)
                        } else {
                            signInResult = SignInResult(
                                error = task.exception?.message ?: "Firebase sign-in failed"
                            )
                        }
                    }
            } else {
                signInResult = SignInResult(error = "Invalid Google Sign-In credentials")
            }
        } catch (e: Exception) {
            Log.e("SignIn", "Error handling sign-in result", e)
            signInResult = SignInResult(error = e.localizedMessage ?: "Sign-in result handling failed")
        }
    }
}