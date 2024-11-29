package com.example.kotlinfoodorder

import android.app.Application
import com.example.kotlinfoodorder.auth.di.loginModule
import com.example.kotlinfoodorder.auth.di.menuModule
import com.example.kotlinfoodorder.auth.di.orderModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(menuModule, loginModule, orderModule)
        }
    }
}