package com.example.kotlinfoodorder.menu.data.repository;

class OrderRepositoryImpl : OrderRepository {
    private val orders: MutableMap<String, MutableList<String>> = mutableMapOf()

    override fun addItemToOrder(userId: String, itemId: String) {
        val userOrder = orders.getOrPut(userId) { mutableListOf() }
        userOrder.add(itemId)
    }

    override fun removeItemFromOrder(userId: String, itemId: String): Boolean {
        val userOrder = orders[userId]
        return userOrder?.remove(itemId) ?: false
    }

    override fun getOrderNumberOfItems(userId: String): Int {
        return orders[userId]?.size ?: 0
    }
}