package com.example.schedule.vm

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schedule.data.model.Holiday
import com.example.schedule.data.model.NetworkUtils
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class HolidayViewModel : ViewModel() {
    private val _holidayList = MutableStateFlow<List<Holiday>>(emptyList())
    val holidayList: StateFlow<List<Holiday>> = _holidayList

    private val _showNoInternet = MutableStateFlow(false)
    val showNoInternet: StateFlow<Boolean> = _showNoInternet

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val database = FirebaseDatabase.getInstance(
        "https://schedule-db715-default-rtdb.asia-southeast1.firebasedatabase.app/"
    ).getReference("data/holiday")

    fun fetchHolidays(context: Context) {
        viewModelScope.launch {
            Log.d("HolidayViewModel", "Checking internet availability...")
            _isLoading.value = true
            _showNoInternet.value = false

            if (!NetworkUtils.isInternetAvailable(context)) {
                Log.w("HolidayViewModel", "No internet connection detected")
                _showNoInternet.value = true
                _isLoading.value = false
                return@launch
            }

            try {
                Log.d("HolidayViewModel", "Fetching holidays from Firebase...")
                val snapshot = withTimeout(5000) { database.get().await() }
                if (snapshot.exists()) {
                    val holidays = snapshot.children.mapNotNull { it.getValue(Holiday::class.java) }
                    _holidayList.value = holidays
                } else {
                    _showNoInternet.value = true  // Show NoInternet composable instead of error
                }
            } catch (e: Exception) {
                Log.e("HolidayViewModel", "Error fetching holidays: ${e.message}")
                _showNoInternet.value = true  // Assume network failure
            } finally {
                _isLoading.value = false
            }
        }
    }
}