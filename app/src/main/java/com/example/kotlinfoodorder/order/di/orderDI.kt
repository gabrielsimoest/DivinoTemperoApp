package com.example.kotlinfoodorder.auth.di

import com.example.kotlinfoodorder.menu.data.repository.MenuCategoryRepository
import com.example.kotlinfoodorder.menu.data.repository.MenuCategoryRepositoryImpl
import com.example.kotlinfoodorder.login.data.MenuItemRepository
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

val orderModule = module {
    factory<OrderRepository> {
        OrderRepositoryImpl(menuItemRemoteDatasource = get(), currentOrderDataSource = get())
    }

    single<CurrentOrderDataSource> {
        CurrentOrderDataSourceImpl(loginRepository = get(), menuItemRemoteDatasource = get())
    }

    viewModel {
        OrderViewModel(loginRepository = get(), orderRepository = get())
    }
}