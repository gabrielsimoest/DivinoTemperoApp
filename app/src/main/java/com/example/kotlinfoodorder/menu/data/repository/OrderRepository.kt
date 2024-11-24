package com.example.kotlinfoodorder.menu.data.repository

interface OrderRepository {
    fun addItemToOrder(userId: String, itemId: String)

    fun removeItemFromOrder(userId: String, itemId: String): Boolean

    fun getOrderNumberOfItems(userId: String): Int
}