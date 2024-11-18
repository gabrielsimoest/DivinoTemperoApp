package com.example.kotlinfoodorder.menuManager.data.repository

import com.example.kotlinfoodorder.menuManager.ui.menu.MenuItem

interface OrderRepository {
    fun addItemToOrder(userId: String, itemId: Int)

    fun removeItemFromOrder(userId: String, itemId: Int): Boolean

    fun getOrderNumberOfItems(userId: String): Int
}