package com.example.schedule.vm

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.schedule.data.model.ClassSchedule
import com.example.schedule.data.model.NetworkUtils
import com.example.schedule.data.model.UserInfo.UserDatabase
import com.example.schedule.data.model.UserInfo.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeekViewModel(application: Application) : AndroidViewModel(application) {
    private val database = UserDatabase.getDatabase(application)
    private val repository = UserRepository(database.userDao())
    private val _showNoInternet = MutableStateFlow(false)
    val showNoInternet: StateFlow<Boolean> = _showNoInternet
    private val _weekSchedule = MutableStateFlow<Map<String, List<Pair<String, String>>>>(emptyMap())
    val weekSchedule: StateFlow<Map<String, List<Pair<String, String>>>> = _weekSchedule

    fun checkInternetAvailability(context: Context) {
        viewModelScope.launch {
            _showNoInternet.value = !NetworkUtils.isInternetAvailable(context)
        }
    }

    fun fetchWeeklySchedule(email: String) {
        viewModelScope.launch {
            val user = repository.getUserByEmail(email)
            val section = user?.section ?: return@launch
            val year = user?.year ?: return@launch
            val formattedYear = year.lowercase().replace(" ", "_")

            val database = Firebase.database("https://schedule-db715-default-rtdb.asia-southeast1.firebasedatabase.app/")
            val path = "data/timetable/$formattedYear/$section"

            val ref = database.getReference(path)
            Log.d("Firebase", "Fetching weekly schedule from: $path")

            ref.get().addOnSuccessListener { snapshot ->
                Log.d("Firebase", "Raw Weekly Snapshot Value: ${snapshot.value}")

                if (snapshot.exists()) {
                    val weekData = mutableMapOf<String, List<Pair<String, String>>>()

                    for (daySnapshot in snapshot.children) {
                        val dayName = daySnapshot.key ?: continue
                        val dailySchedule = daySnapshot.children.mapNotNull {
                            val subject = it.child("subject").value as? String
                            val room = it.child("room").value as? String

                            if (subject != null && room != null) {
                                subject to room
                            } else {
                                Log.e("Firebase", "Skipping invalid class data: $it")
                                null
                            }
                        }
                        weekData[dayName] = dailySchedule
                    }
                    _weekSchedule.value = weekData
                    Log.d("Firebase", "Fetched Weekly Schedule: $weekData")
                } else {
                    Log.e("Firebase", "No weekly schedule found for $section")
                }
            }.addOnFailureListener {
                Log.e("Firebase", "Failed to fetch weekly schedule", it)
            }
        }
    }
}