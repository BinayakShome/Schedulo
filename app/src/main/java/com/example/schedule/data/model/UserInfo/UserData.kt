package com.example.schedule.data.model.UserInfo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
data class UserData(
    @PrimaryKey val email: String,
    val year: String,
    val branch: String,
    val section: String
)