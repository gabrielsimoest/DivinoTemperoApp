package com.example.kotlinfoodorder.auth.di

import com.example.kotlinfoodorder.menu.data.repository.MenuCategoryRepository
import com.example.kotlinfoodorder.menu.data.repository.MenuCategoryRepositoryImpl
import com.example.kotlinfoodorder.login.data.MenuItemRepository
import com.example.kotlinfoodorder.login.data.remote.UserRemoteDatasource
import com.example.kotlinfoodorder.login.data.remote.UserRemoteDatasourceImpl
import com.example.kotlinfoodorder.menu.data.remote.MenuItemRemoteDatasource
import com.example.kotlinfoodorder.menu.data.remote.MenuItemRemoteDatasourceImpl
import com.example.kotlinfoodorder.menu.data.repository.MenuItemRepositoryImpl
import com.example.kotlinfoodorder.menu.data.repository.OrderRepository
import com.example.kotlinfoodorder.menu.data.repository.OrderRepositoryImpl
import com.example.kotlinfoodorder.menu.ui.menu.MenuViewModel
import com.example.kotlinfoodorder.menu.ui.menuDetail.MenuDetailViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val menuModule = module {
    factory<MenuItemRemoteDatasource> {
        MenuItemRemoteDatasourceImpl(firestore = Firebase.firestore)
    }

    factory<MenuItemRepository> {
        MenuItemRepositoryImpl(menuItemRemoteDatasource = get())
    }

    factory<MenuCategoryRepository> {
        MenuCategoryRepositoryImpl()
    }

    factory<MenuCategoryRepository> {
        MenuCategoryRepositoryImpl()
    }

    factory<OrderRepository> {
        OrderRepositoryImpl()
    }

    viewModel {
        MenuViewModel(loginRepository = get(), menuCategoryRepository = get(), orderRepository = get())
    }

    viewModel {
        MenuDetailViewModel(loginRepository = get(), menuItemRepository = get(), orderRepository = get())
    }
}