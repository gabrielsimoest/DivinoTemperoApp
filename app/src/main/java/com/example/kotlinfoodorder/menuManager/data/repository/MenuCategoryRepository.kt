package com.example.kotlinfoodorder.menuManager.data.repository

import com.example.kotlinfoodorder.menuManager.ui.menu.MenuCategoryModel

interface MenuCategoryRepository {
    suspend fun getMenuCategories(): List<MenuCategoryModel>
}