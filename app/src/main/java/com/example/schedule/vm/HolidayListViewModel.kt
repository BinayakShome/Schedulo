package com.example.schedule.vm

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schedule.data.model.Holiday
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class HolidayViewModel : ViewModel() {
    private val _holidayList = MutableStateFlow<List<Holiday>>(emptyList())
    val holidayList: StateFlow<List<Holiday>> = _holidayList
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val database = FirebaseDatabase.getInstance("https://schedule-db715-default-rtdb.asia-southeast1.firebasedatabase.app/")
        .getReference("data/holiday")

    fun fetchHolidays() {
        viewModelScope.launch {
            Log.d("HolidayViewModel", "Fetching holidays from Firebase...")
            _isLoading.value = true
            _errorMessage.value = null
            try {
                Log.d("HolidayViewModel", "Awaiting Firebase snapshot with timeout...")
                val snapshot = withTimeout(5000) { database.get().await() }
                Log.d("HolidayViewModel", "Snapshot received: ${snapshot.exists()}")
                if (snapshot.exists()) {
                    val holidays = snapshot.children.mapNotNull {
                        val holiday = it.getValue(Holiday::class.java)
                        Log.d("HolidayViewModel", "Parsed holiday: $holiday")
                        holiday
                    }
                    _holidayList.value = holidays
                    Log.d("HolidayViewModel", "Holiday list updated with ${holidays.size} items")
                } else {
                    _errorMessage.value = "No holidays available"
                    Log.w("HolidayViewModel", "No holidays found in the database")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load holidays: ${e.message}"
                Log.e("HolidayViewModel", "Error loading holidays: ${e.message}", e)
            } finally {
                _isLoading.value = false
                Log.d("HolidayViewModel", "Loading state set to false")
            }
        }
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}