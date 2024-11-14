package com.example.kotlinfoodorder.login.data

import com.example.kotlinfoodorder.login.data.remote.LoginRemoteDatasource
import com.example.kotlinfoodorder.login.model.UserAuth

class LoginRepositoryImpl(
    private val remoteDatasource: LoginRemoteDatasource
): LoginRepository {
    override fun isSessionValid(): Boolean {
        return remoteDatasource.isSessionValid()
    }

    override suspend fun createAccount(name: String, email: String, password: String): UserAuth {
        return remoteDatasource.createAccount(name, email, password)
    }

    override suspend fun login(email: String, password: String): UserAuth {
        return remoteDatasource.signIn(email, password)
    }

    override suspend fun recover(email: String) {
        remoteDatasource.recover(email)
    }
}