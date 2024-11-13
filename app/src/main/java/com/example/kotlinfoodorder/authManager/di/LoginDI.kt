package com.example.kotlinfoodorder.authManager.di

import com.example.kotlinfoodorder.authManager.ui.login.LoginViewModel
import com.example.kotlinfoodorder.login.data.LoginRepository
import com.example.kotlinfoodorder.login.data.LoginRepositoryImpl
import com.example.kotlinfoodorder.login.data.remote.LoginRemoteDatasource
import com.example.kotlinfoodorder.login.data.remote.LoginRemoteDatasourceImpl
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    factory<LoginRemoteDatasource> {
        LoginRemoteDatasourceImpl(firebaseAuth = FirebaseAuth.getInstance())
    }

    factory<LoginRepository> {
        LoginRepositoryImpl(remoteDatasource = get())
    }

    viewModel {
        LoginViewModel(loginRepository = get())
    }
}