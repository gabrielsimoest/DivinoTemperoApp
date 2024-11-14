package com.example.kotlinfoodorder.login.data

import com.example.kotlinfoodorder.login.model.UserAuth

interface LoginRepository {
    fun isSessionValid(): Boolean
    suspend fun createAccount(name: String, email: String, password: String): UserAuth
    suspend fun login(email: String, password: String): UserAuth
    suspend fun recover(email: String)
}