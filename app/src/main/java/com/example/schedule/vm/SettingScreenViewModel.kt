package com.example.schedule.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.schedule.data.model.UserInfo.UserData
import com.example.schedule.data.model.UserInfo.UserDatabase
import com.example.schedule.data.model.UserInfo.UserRepository
import kotlinx.coroutines.launch

class SettingScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val database = UserDatabase.getDatabase(application)
    private val repository = UserRepository(database.userDao())

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

//    fun clearUsers() {
//        viewModelScope.launch {
//            repository.clearUsers()
//        }
//    }
}
