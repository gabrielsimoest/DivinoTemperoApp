package com.example.kotlinfoodorder.menu.data.repository

import com.example.kotlinfoodorder.login.data.MenuItemRepository
import com.example.kotlinfoodorder.menu.data.remote.MenuItemRemoteDatasource
import com.example.kotlinfoodorder.order.model.OrderItem
import com.example.kotlinfoodorder.order.model.OrderModel
import com.example.kotlinfoodorder.menu.model.MenuItem

class MenuItemRepositoryImpl(
    private val menuItemRemoteDatasource: MenuItemRemoteDatasource
): MenuItemRepository {
    override suspend fun getMenuItems(): List<MenuItem> {
        return menuItemRemoteDatasource.getMenuItems()
    }

    override suspend fun getMenuItem(id: String): MenuItem? {
        return menuItemRemoteDatasource.getMenuItem(id)
    }

    override suspend fun getMenuOrderItems(orders: List<OrderModel>): List<OrderItem>? {
        return menuItemRemoteDatasource.getMenuOrderItems(orders)
    }
}