package com.example.kotlinfoodorder.login.data

import com.example.kotlinfoodorder.menu.model.OrderItem
import com.example.kotlinfoodorder.menu.model.OrderModel
import com.example.kotlinfoodorder.menu.model.MenuItem

interface MenuItemRepository {
    suspend fun getMenuItems(): List<MenuItem>
    suspend fun getMenuItem(id: String): MenuItem?
    suspend fun getMenuOrderItems(orders: List<OrderModel>): List<OrderItem>?
}