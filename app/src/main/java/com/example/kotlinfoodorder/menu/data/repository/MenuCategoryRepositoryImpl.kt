package com.example.kotlinfoodorder.menu.data.repository

import com.example.kotlinfoodorder.menu.data.remote.MenuCategoryRemoteDatasource
import com.example.kotlinfoodorder.menu.data.remote.MenuItemRemoteDatasource
import com.example.kotlinfoodorder.menu.model.MenuCategoryModel

class MenuCategoryRepositoryImpl(
    private val menuCategoryRemoteDatasource: MenuCategoryRemoteDatasource
): MenuCategoryRepository {
    override suspend fun getMenuCategories(): List<MenuCategoryModel>{
        val list = menuCategoryRemoteDatasource.getMenuCategories()
        return listOf(
            MenuCategoryModel(id = "todos", name = "Todos"),
        ) + list
    }
}