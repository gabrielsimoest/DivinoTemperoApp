package com.example.kotlinfoodorder.auth.di

import com.example.kotlinfoodorder.menu.data.repository.MenuCategoryRepository
import com.example.kotlinfoodorder.menu.data.repository.MenuCategoryRepositoryImpl
import com.example.kotlinfoodorder.login.data.MenuItemRepository
import com.example.kotlinfoodorder.menu.data.remote.MenuCategoryRemoteDatasource
import com.example.kotlinfoodorder.menu.data.remote.MenuCategoryRemoteDatasourceImpl
import com.example.kotlinfoodorder.order.data.memory.CurrentOrderDataSource
import com.example.kotlinfoodorder.order.data.memory.CurrentOrderDataSourceImpl
import com.example.kotlinfoodorder.menu.data.remote.MenuItemRemoteDatasource
import com.example.kotlinfoodorder.menu.data.remote.MenuItemRemoteDatasourceImpl
import com.example.kotlinfoodorder.menu.data.repository.MenuItemRepositoryImpl
import com.example.kotlinfoodorder.order.data.repository.OrderRepository
import com.example.kotlinfoodorder.order.data.repository.OrderRepositoryImpl
import com.example.kotlinfoodorder.menu.ui.menu.MenuViewModel
import com.example.kotlinfoodorder.menu.ui.menuDetail.MenuDetailViewModel
import com.example.kotlinfoodorder.order.ui.order.OrderViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val menuModule = module {
    factory<MenuItemRemoteDatasource> {
        MenuItemRemoteDatasourceImpl(firestore = Firebase.firestore)
    }

    factory<MenuCategoryRemoteDatasource> {
        MenuCategoryRemoteDatasourceImpl(firestore = Firebase.firestore)
    }

    factory<MenuItemRepository> {
        MenuItemRepositoryImpl(menuItemRemoteDatasource = get())
    }

    factory<MenuCategoryRepository> {
        MenuCategoryRepositoryImpl(menuCategoryRemoteDatasource = get())
    }

    factory<MenuCategoryRepository> {
        MenuCategoryRepositoryImpl(menuCategoryRemoteDatasource = get())
    }

    viewModel {
        MenuViewModel(loginRepository = get(), menuCategoryRepository = get(), orderRepository = get())
    }

    viewModel {
        MenuDetailViewModel(loginRepository = get(), menuItemRepository = get(), orderRepository = get())
    }
}