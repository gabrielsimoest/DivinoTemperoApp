package com.example.kotlinfoodorder.menu.data.repository

import com.example.kotlinfoodorder.menu.model.OrderModel

interface OrderRepository {
    suspend fun addItemToOrder(userId: String, itemId: String)

    suspend fun removeItemFromOrder(userId: String, itemId: String)

    suspend fun getOrderNumberOfItems(userId: String): Int

    suspend fun getOrderItems(userId: String): List<OrderModel>
}