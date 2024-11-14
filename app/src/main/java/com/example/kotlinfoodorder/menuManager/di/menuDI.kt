package com.example.kotlinfoodorder.authManager.di

import com.example.kotlinfoodorder.login.data.MenuCategoryRepository
import com.example.kotlinfoodorder.login.data.MenuCategoryRepositoryImpl
import com.example.kotlinfoodorder.login.data.MenuItemRepository
import com.example.kotlinfoodorder.login.data.MenuItemRepositoryImpl
import com.example.kotlinfoodorder.menuManager.ui.menu.MenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val menuModule = module {
    factory<MenuItemRepository> {
        MenuItemRepositoryImpl()
    }

    factory<MenuCategoryRepository> {
        MenuCategoryRepositoryImpl()
    }

    viewModel {
        MenuViewModel(loginRepository = get(), menuCategoryRepository = get())
    }
}