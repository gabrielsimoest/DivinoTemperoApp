package com.example.kotlinfoodorder.login.data.remote

import com.example.kotlinfoodorder.login.model.UserAuth

interface LoginRemoteDatasource {
    suspend fun createAccount(email: String, password: String): UserAuth
    suspend fun signIn(email: String, password: String): UserAuth
    suspend fun recover(email: String)
    fun isSessionValid(): Boolean
}