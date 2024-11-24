package com.example.kotlinfoodorder.menu.data.repository

import com.example.kotlinfoodorder.menu.model.MenuCategoryModel

interface MenuCategoryRepository {
    suspend fun getMenuCategories(): List<MenuCategoryModel>
}