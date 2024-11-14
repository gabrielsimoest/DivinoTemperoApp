package com.example.kotlinfoodorder.login.data

import com.example.kotlinfoodorder.menuManager.ui.menu.MenuCategoryModel

interface MenuCategoryRepository {
    suspend fun getMenuCategories(): List<MenuCategoryModel>
}