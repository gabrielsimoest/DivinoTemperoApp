package com.example.kotlinfoodorder.login.data

import com.example.kotlinfoodorder.login.model.User

interface UserRepository {
    fun isSessionValid(): Boolean
    suspend fun createAccount(name: String, email: String, password: String): User
    suspend fun login(email: String, password: String): User
    suspend fun logout()
    suspend fun getCurrentUserInfo(): User
    suspend fun recover(email: String)
}