package com.example.schedule.data.model.UserInfo

class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser(user: UserData) {
        userDao.insertUser(user)
    }

    suspend fun getUserByEmail(email: String): UserData? {
        return userDao.getUserByEmail(email)
    }

//    suspend fun clearUsers() {
//        userDao.deleteUser()
//    }
}

