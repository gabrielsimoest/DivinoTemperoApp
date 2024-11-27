package com.example.kotlinfoodorder.login.data

import com.example.kotlinfoodorder.auth.data.remote.UserRemoteDatasource
import com.example.kotlinfoodorder.login.model.User

class UserRepositoryImpl(
    private val remoteDatasource: UserRemoteDatasource
): UserRepository {
    override fun isSessionValid(): Boolean {
        return remoteDatasource.isSessionValid()
    }

    override suspend fun createAccount(name: String, email: String, password: String): User {
        return remoteDatasource.createAccount(name, email, password)
    }

    override suspend fun login(email: String, password: String): User {
        return remoteDatasource.signIn(email, password)
    }

    override suspend fun logout() {
        return remoteDatasource.signOut()
    }

    override suspend fun getCurrentUserInfo(): User {
        return remoteDatasource.getCurrentUserInfo()
    }

    override suspend fun recover(email: String) {
        remoteDatasource.recover(email)
    }
}