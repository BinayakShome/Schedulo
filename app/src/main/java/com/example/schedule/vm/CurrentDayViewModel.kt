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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CurrentDayViewModel(application: Application) : AndroidViewModel(application) {
    private val database = UserDatabase.getDatabase(application)
    private val repository = UserRepository(database.userDao())

    private val _showNoInternet = MutableStateFlow(false)
    val showNoInternet: StateFlow<Boolean> = _showNoInternet

    private val _classSchedule = MutableStateFlow<List<ClassSchedule>>(emptyList())
    val classSchedule: StateFlow<List<ClassSchedule>> = _classSchedule

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun checkInternetAvailability(context: Context) {
        viewModelScope.launch {
            _showNoInternet.value = !NetworkUtils.isInternetAvailable(context)
        }
    }

    fun fetchCurrentDaySchedule(email: String) {
        viewModelScope.launch {
            _isLoading.value = true // Start loading

            val user = repository.getUserByEmail(email)
            val section = user?.section ?: return@launch
            val year = user?.year ?: return@launch
            val formattedYear = year.lowercase().replace(" ", "_")

            val fullDay = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())
            val shortDay = fullDay.uppercase(Locale.ENGLISH).take(3)

            val database = Firebase.database("https://schedule-db715-default-rtdb.asia-southeast1.firebasedatabase.app/")
            val path = "data/timetable/$formattedYear/$section/$shortDay"

            val ref = database.getReference(path)
            Log.d("Firebase", "Fetching schedule from: $path")

            ref.get().addOnSuccessListener { snapshot ->
                Log.d("Firebase", "Raw Snapshot Value: ${snapshot.value}")

                if (snapshot.exists()) {
                    val fetchedSchedule = snapshot.children.mapNotNull {
                        val subject = it.child("subject").value as? String
                        val time = it.child("time").value as? String
                        val room = it.child("room").value as? String

                        if (subject != null && time != null && room != null) {
                            val campus = getCampusFromRoom(room)
                            val formattedTime = formatTime(time)
                            ClassSchedule(subject, formattedTime, room, campus)
                        } else {
                            Log.e("Firebase", "Skipping invalid class data: $it")
                            null
                        }
                    }

                    if (fetchedSchedule.isNotEmpty()) {
                        _classSchedule.value = fetchedSchedule.sortedBy { extractStartHour(it.time) }
                        Log.d("Firebase", "Fetched Schedule: $_classSchedule")
                    } else {
                        Log.e("Firebase", "Classes exist but data is incorrectly formatted!")
                    }
                } else {
                    Log.e("Firebase", "No schedule found for $section on $shortDay (Empty snapshot)")
                }
                _isLoading.value = false // Stop loading after success or failure
            }.addOnFailureListener {
                Log.e("Firebase", "Failed to fetch schedule", it)
                _isLoading.value = false // Stop loading on failure
            }
        }
    }

    private fun formatTime(time: String): String {
        val times = time.split("-").mapNotNull { it.toIntOrNull() }
        if (times.size != 2) return time

        val startHour = times[0]
        val endHour = times[1]

        return "${formatHour(startHour)} - ${formatHour(endHour)}"
    }

    private fun formatHour(hour: Int): String {
        return when {
            hour in 1..7 -> "$hour:00 PM"
            hour in 8..11 -> "$hour:00 AM"
            hour == 12 -> "12:00 PM"
            hour in 13..17 -> "${hour - 12}:00 PM"
            else -> "$hour:00"
        }
    }

    private fun extractStartHour(time: String): Int {
        return time.split("-").firstOrNull()?.toIntOrNull() ?: Int.MAX_VALUE
    }

    fun getCampusFromRoom(room: String): String {
        return when {
            room.startsWith("C", ignoreCase = true) -> "14"
            room.startsWith("A", ignoreCase = true) || room.startsWith("B", ignoreCase = true) -> "15"
            room.startsWith("ETC", ignoreCase = true) -> "12"
            else -> "Unknown"
        }
    }
}