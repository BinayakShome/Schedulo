package com.example.schedule.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.schedule.data.model.UserInfo.UserData
import com.example.schedule.data.model.UserInfo.UserDatabase
import com.example.schedule.data.model.UserInfo.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlinx.coroutines.launch

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val database = UserDatabase.getDatabase(application)
    private val repository = UserRepository(database.userDao())

    private val _sections = MutableStateFlow<List<String>>(emptyList()) // Holds sections
    val sections: StateFlow<List<String>> = _sections

    fun insertUser(user: UserData) {
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }

    fun getUser(email: String, callback: (UserData?) -> Unit) {
        viewModelScope.launch {
            val user = repository.getUserByEmail(email)
            callback(user)
        }
    }

    fun fetchSectionsFromFirebase(year: String, branch: String) {
        if (year == "Select your Year" || branch == "Select your Branch") {
            _sections.value = emptyList()
            return
        }

        val formattedYear = year.lowercase().replace(" ", "_")
        val database = Firebase.database("https://schedule-db715-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val path = "data/timetable/$formattedYear"
        val ref = database.getReference(path)

        Log.d("Firebase", "Fetching sections from: $path")

        ref.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val fetchedSections = snapshot.children.mapNotNull { it.key }
                    .filter { it.startsWith(branch) } // 🔥 Filter sections that start with the selected Branch
                    .sortedBy { it.split("-").getOrNull(1)?.toIntOrNull() ?: Int.MAX_VALUE } // 🔥 Sort numerically

                _sections.value = fetchedSections
                Log.d("Firebase", "Sorted Sections: $fetchedSections")
            } else {
                Log.e("Firebase", "Snapshot does not exist at $path")
            }
        }.addOnFailureListener {
            Log.e("Firebase", "Failed to fetch sections", it)
        }
    }
}
