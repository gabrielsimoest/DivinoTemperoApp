package com.example.kotlinfoodorder.menu.data.repository

import com.example.kotlinfoodorder.menu.model.OrderModel
import kotlinx.coroutines.flow.StateFlow

interface OrderRepository {
    suspend fun addItemToOrder(userId: String, itemId: String)

    suspend fun removeItemFromOrder(userId: String, itemId: String)

    fun getOrderNumberOfItems(): StateFlow<Int>

    suspend fun getOrderItems(userId: String): List<OrderModel>
}