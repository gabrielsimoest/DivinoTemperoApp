package com.example.kotlinfoodorder.auth.di

import com.example.kotlinfoodorder.auth.ui.forgotpassword.ForgotPasswordViewModel
import com.example.kotlinfoodorder.auth.ui.login.LoginViewModel
import com.example.kotlinfoodorder.auth.ui.register.RegisterViewModel
import com.example.kotlinfoodorder.login.data.UserRepository
import com.example.kotlinfoodorder.login.data.UserRepositoryImpl
import com.example.kotlinfoodorder.auth.data.remote.UserRemoteDatasource
import com.example.kotlinfoodorder.login.data.remote.UserRemoteDatasourceImpl
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    factory<UserRemoteDatasource> {
        UserRemoteDatasourceImpl(firebaseAuth = FirebaseAuth.getInstance(), firestore = Firebase.firestore)
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