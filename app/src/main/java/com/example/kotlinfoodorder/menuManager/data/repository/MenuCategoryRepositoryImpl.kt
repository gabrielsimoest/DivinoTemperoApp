package com.example.kotlinfoodorder.login.data

import com.example.kotlinfoodorder.menuManager.ui.menu.MenuCategoryModel

class MenuCategoryRepositoryImpl(
): MenuCategoryRepository {
    override suspend fun getMenuCategories(): List<MenuCategoryModel>{
        return listOf(
            MenuCategoryModel(id = 1, name = "Todos"),
            MenuCategoryModel(id = 2, name = "Comidas"),
            MenuCategoryModel(id = 3, name = "Bebidas"),
            MenuCategoryModel(id = 4, name = "Sobremesas"),
            MenuCategoryModel(id = 5, name = "Outros")
        )
    }
}