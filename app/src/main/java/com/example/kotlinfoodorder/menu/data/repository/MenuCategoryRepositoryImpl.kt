package com.example.kotlinfoodorder.menu.data.repository

import com.example.kotlinfoodorder.menu.model.MenuCategoryModel

class MenuCategoryRepositoryImpl(
): MenuCategoryRepository {
    override suspend fun getMenuCategories(): List<MenuCategoryModel>{
        return listOf(
            MenuCategoryModel(id = "todos", name = "Todos"),
            MenuCategoryModel(id = "comidas", name = "Comidas"),
            MenuCategoryModel(id = "bebidas", name = "Bebidas"),
            MenuCategoryModel(id = "sobremesas", name = "Sobremesas")
        )
    }
}