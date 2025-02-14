package com.example.schedule.vm

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schedule.R
import com.example.schedule.data.model.SignInResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var signInResult by mutableStateOf<SignInResult?>(null)
        private set

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    fun initGoogleSignIn(context: Activity) {
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

    fun startGoogleSignIn(launcher: ActivityResultLauncher<IntentSenderRequest>) {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                launcher.launch(IntentSenderRequest.Builder(result.pendingIntent).build())
            }
            .addOnFailureListener {
                signInResult = SignInResult(error = "No Google accounts found.")
            }
    }

    fun handleSignInResult(data: Intent?) {
        val credential = oneTapClient.getSignInCredentialFromIntent(data)
        val idToken = credential.googleIdToken

        if (idToken != null) {
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            viewModelScope.launch {
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
                                    .addOnSuccessListener { Log.d("Firestore", "User added") }
                                    .addOnFailureListener { e -> Log.e("Firestore", "Error adding user", e) }
                            }
                            signInResult = SignInResult(success = auth)
                        } else {
                            signInResult = SignInResult(error = task.exception?.message ?: "Sign-in failed")
                        }
                    }
            }
        } else {
            signInResult = SignInResult(error = "Invalid sign-in credentials")
        }
    }
}