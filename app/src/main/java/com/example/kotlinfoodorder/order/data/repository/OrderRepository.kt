package com.example.kotlinfoodorder.order.data.repository

import com.example.kotlinfoodorder.order.model.OrderModel
import kotlinx.coroutines.flow.StateFlow

interface OrderRepository {
    suspend fun addItemToOrder(userId: String, itemId: String)

    suspend fun removeItemFromOrder(userId: String, itemId: String)

    fun getOrderNumberOfItems(): StateFlow<Int>

    suspend fun getOrderItems(userId: String): List<OrderModel>

    suspend fun placeOrder(userId: String)
}