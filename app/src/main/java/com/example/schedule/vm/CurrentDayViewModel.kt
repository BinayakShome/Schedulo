package com.example.schedule.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schedule.data.model.NetworkUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CurrentDayViewModel : ViewModel() {
    private val _showNoInternet = MutableStateFlow(false)
    val showNoInternet: StateFlow<Boolean> = _showNoInternet

    fun checkInternetAvailability(context: Context) {
        viewModelScope.launch {
            _showNoInternet.value = !NetworkUtils.isInternetAvailable(context)
        }
    }
}
