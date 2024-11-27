package com.example.kotlinfoodorder.auth.data.remote

import com.example.kotlinfoodorder.login.model.User

interface UserRemoteDatasource {
    suspend fun createAccount(name: String, email: String, password: String): User
    suspend fun signIn(email: String, password: String): User
    suspend fun recover(email: String)
    suspend fun signOut()
    suspend fun getCurrentUserInfo(): User
    fun isSessionValid(): Boolean
}