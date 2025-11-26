package com.example.simplemvvmroom.model

import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao)
{
    val allUsers: Flow<List<User>> = userDao.getAllUsers()
    suspend fun insertUser(user: User)
    {
        userDao.insertUser(user)
    }
}