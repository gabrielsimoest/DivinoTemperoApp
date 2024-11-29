package com.example.kotlinfoodorder.menu.data.remote

import com.example.kotlinfoodorder.order.model.OrderItem
import com.example.kotlinfoodorder.order.model.OrderModel
import com.example.kotlinfoodorder.menu.model.MenuItem

interface MenuItemRemoteDatasource {
    suspend fun getMenuItems(): List<MenuItem>
    suspend fun getMenuItem(id: String): MenuItem?
    suspend fun getMenuOrderItems(orders: List<OrderModel>): List<OrderItem>?

    suspend fun addOrderItem(userId: String, menuId: String)
    suspend fun removeOrderItem(userId: String, menuId: String)
    suspend fun getOrderNumberOfItems(userId: String) : Int
    suspend fun getOrderItems(userId: String): List<OrderModel>
    suspend fun placeOrder(userId: String)
}