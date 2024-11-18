package com.example.kotlinfoodorder.menuManager.data.repository;

class OrderRepositoryImpl : OrderRepository {
    private val orders: MutableMap<String, MutableList<Int>> = mutableMapOf()

    override fun addItemToOrder(userId: String, itemId: Int) {
        val userOrder = orders.getOrPut(userId) { mutableListOf() }
        userOrder.add(itemId)
    }

    override fun removeItemFromOrder(userId: String, itemId: Int): Boolean {
        val userOrder = orders[userId]
        return userOrder?.remove(itemId) ?: false
    }

    override fun getOrderNumberOfItems(userId: String): Int {
        return orders[userId]?.size ?: 0
    }
}