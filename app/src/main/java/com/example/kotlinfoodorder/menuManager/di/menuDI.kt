package com.example.kotlinfoodorder.authManager.di

import com.example.kotlinfoodorder.menuManager.data.repository.MenuCategoryRepository
import com.example.kotlinfoodorder.menuManager.data.repository.MenuCategoryRepositoryImpl
import com.example.kotlinfoodorder.login.data.MenuItemRepository
import com.example.kotlinfoodorder.menuManager.data.repository.MenuItemRepositoryImpl
import com.example.kotlinfoodorder.menuManager.data.repository.OrderRepository
import com.example.kotlinfoodorder.menuManager.data.repository.OrderRepositoryImpl
import com.example.kotlinfoodorder.menuManager.ui.menu.MenuViewModel
import com.example.kotlinfoodorder.menuManager.ui.menuDetail.MenuDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val menuModule = module {
    factory<MenuItemRepository> {
        MenuItemRepositoryImpl()
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