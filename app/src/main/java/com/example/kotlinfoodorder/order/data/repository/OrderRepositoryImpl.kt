package com.example.kotlinfoodorder.order.data.repository

import com.example.kotlinfoodorder.order.data.memory.CurrentOrderDataSource
import com.example.kotlinfoodorder.menu.data.remote.MenuItemRemoteDatasource
import com.example.kotlinfoodorder.order.model.OrderModel
import kotlinx.coroutines.flow.StateFlow

class OrderRepositoryImpl(
    private val menuItemRemoteDatasource: MenuItemRemoteDatasource,
    private val currentOrderDataSource: CurrentOrderDataSource
) : OrderRepository {
    override suspend fun addItemToOrder(userId: String, itemId: String) {
        menuItemRemoteDatasource.addOrderItem(userId, itemId)
        currentOrderDataSource.updateOrderNumber()
    }

    override suspend fun removeItemFromOrder(userId: String, itemId: String) {
        menuItemRemoteDatasource.removeOrderItem(userId, itemId)
        currentOrderDataSource.updateOrderNumber()
    }

    override fun getOrderNumberOfItems(): StateFlow<Int> {
        return currentOrderDataSource.currentOrder
    }

    override suspend fun getOrderItems(userId: String): List<OrderModel> {
        return menuItemRemoteDatasource.getOrderItems(userId)
    }

    override suspend fun placeOrder(userId: String) {
        menuItemRemoteDatasource.placeOrder(userId)
        currentOrderDataSource.updateOrderNumber()
    }
}