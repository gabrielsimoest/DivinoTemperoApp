package com.example.kotlinfoodorder.menu.data.remote

import com.example.kotlinfoodorder.menu.model.MenuCategoryModel
import com.example.kotlinfoodorder.order.model.OrderItem
import com.example.kotlinfoodorder.order.model.OrderModel
import com.example.kotlinfoodorder.menu.model.MenuItem

interface MenuCategoryRemoteDatasource {
    suspend fun getMenuCategories(): List<MenuCategoryModel>
}