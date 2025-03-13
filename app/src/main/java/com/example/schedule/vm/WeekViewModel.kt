package com.example.schedule.vm

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.schedule.data.model.ClassSchedule
import com.example.schedule.data.model.NetworkUtils
import com.example.schedule.data.model.UserInfo.UserDatabase
import com.example.schedule.data.model.UserInfo.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeekViewModel (application: Application) : AndroidViewModel(application){
    private val database = UserDatabase.getDatabase(application)
    private val repository = UserRepository(database.userDao())

    private val _showNoInternet = MutableStateFlow(false)
    val showNoInternet: StateFlow<Boolean> = _showNoInternet

    fun checkInternetAvailability(context: Context) {
        viewModelScope.launch {
            _showNoInternet.value = !NetworkUtils.isInternetAvailable(context)
        }
    }
}