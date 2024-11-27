package com.example.kotlinfoodorder.menu.data.repository

import com.example.kotlinfoodorder.menu.data.remote.MenuItemRemoteDatasource
import com.example.kotlinfoodorder.menu.model.OrderModel

class OrderRepositoryImpl(
    private val menuItemRemoteDatasource: MenuItemRemoteDatasource
) : OrderRepository {
    override suspend fun addItemToOrder(userId: String, itemId: String) {
        menuItemRemoteDatasource.addOrderItem(userId, itemId)
    }

    override suspend fun removeItemFromOrder(userId: String, itemId: String) {
        menuItemRemoteDatasource.removeOrderItem(userId, itemId)
    }

    override suspend fun getOrderNumberOfItems(userId: String): Int {
        return menuItemRemoteDatasource.getOrderNumberOfItems(userId)
    }

    override suspend fun getOrderItems(userId: String): List<OrderModel> {
        return menuItemRemoteDatasource.getOrderItems(userId)
    }
}