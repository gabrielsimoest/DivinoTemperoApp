package com.example.kotlinfoodorder.authManager.di

import com.example.kotlinfoodorder.authManager.ui.forgotpassword.ForgotPasswordViewModel
import com.example.kotlinfoodorder.authManager.ui.login.LoginViewModel
import com.example.kotlinfoodorder.authManager.ui.register.RegisterViewModel
import com.example.kotlinfoodorder.login.data.UserRepository
import com.example.kotlinfoodorder.login.data.UserRepositoryImpl
import com.example.kotlinfoodorder.login.data.remote.UserRemoteDatasource
import com.example.kotlinfoodorder.login.data.remote.UserRemoteDatasourceImpl
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    factory<UserRemoteDatasource> {
        UserRemoteDatasourceImpl(firebaseAuth = FirebaseAuth.getInstance())
    }

    factory<UserRepository> {
        UserRepositoryImpl(remoteDatasource = get())
    }

    viewModel {
        LoginViewModel(loginRepository = get())
    }

    viewModel {
        ForgotPasswordViewModel(loginRepository = get())
    }

    viewModel {
        RegisterViewModel(loginRepository = get())
    }
}