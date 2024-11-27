package com.example.kotlinfoodorder.menu.data.remote

import android.util.Log
import com.example.kotlinfoodorder.menu.model.OrderItem
import com.example.kotlinfoodorder.menu.model.OrderModel
import com.example.kotlinfoodorder.menu.model.MenuItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MenuItemRemoteDatasourceImpl(
    private val firestore: FirebaseFirestore
) : MenuItemRemoteDatasource {
    override suspend fun getMenuItems(): List<MenuItem> {
        return try {
            val result = firestore.collection("menu").get().await()

            val menuItems = mutableListOf<MenuItem>()

            for (document in result) {
                val menuItem = document.toObject(MenuItem::class.java)
                menuItems.add(menuItem)
            }

            menuItems
        } catch (exception: Exception) {
            emptyList()
        }
    }

    override suspend fun getMenuOrderItems(orders: List<OrderModel>): List<OrderItem>? {
        return try {
            if (orders.isEmpty())
                return emptyList()

            val orderIds = orders.map { it.menuId }

            val query = firestore.collection("menu")
                .whereIn("id", orderIds)

            val result = query.get().await()

            val orderQuantityMap = orders.associateBy({ it.menuId }, { it.quantity })

            val menuItems = result.map { document ->
                val menuItem = document.toObject(OrderItem::class.java)
                menuItem.quantity = orderQuantityMap[menuItem.id] ?: 0
                menuItem
            }

            menuItems
        } catch (exception: Exception) {
            Log.e("MenuItemRemoteDatasource", "Erro ao buscar os itens do menu", exception)
            emptyList()
        }
    }


    override suspend fun getMenuItem(id: String): MenuItem? {
        return try {
            val document = firestore.collection("menu")
                .whereEqualTo("id", id)
                .get()
                .await()

            if (!document.isEmpty) {
                document.documents.first().toObject(MenuItem::class.java)
            } else {
                null
            }
        } catch (exception: Exception) {
            Log.e("MenuItemRemoteDatasource", "Erro ao buscar o item do menu", exception)
            null
        }
    }

    override suspend fun addOrderItem(userId: String, menuId: String) {
        try {
            val orderCollection = firestore.collection("order")

            val result = orderCollection
                .whereEqualTo("userId", userId)
                .whereEqualTo("menuId", menuId)
                .get()
                .await()

            if (!result.isEmpty) {
                val orderDocument = result.documents.first()
                val existingOrder = orderDocument.toObject(OrderModel::class.java)
                val updatedQuantity = (existingOrder?.quantity ?: 0) + 1

                if (updatedQuantity > 0) {
                    orderDocument.reference.update("quantity", updatedQuantity).await()
                } else {
                    orderDocument.reference.delete().await()
                }
            } else {
                val newOrder = OrderModel(
                    userId = userId,
                    menuId = menuId,
                    quantity = 1
                )
                orderCollection.add(newOrder).await()
            }
        } catch (exception: Exception) {
            Log.e("MenuItemRemoteDatasource", "Erro ao adicionar ou atualizar o item no pedido", exception)
        }
    }

    override suspend fun removeOrderItem(userId: String, menuId: String) {
        try {
            val orderCollection = firestore.collection("order")

            val result = orderCollection
                .whereEqualTo("userId", userId)
                .whereEqualTo("menuId", menuId)
                .get()
                .await()

            if (!result.isEmpty) {
                val orderDocument = result.documents.first()
                val existingOrder = orderDocument.toObject(OrderModel::class.java)
                val updatedQuantity = (existingOrder?.quantity ?: 0) - 1

                if (updatedQuantity > 0) {
                    orderDocument.reference.update("quantity", updatedQuantity).await()
                } else {
                    orderDocument.reference.delete().await()
                }
            } else {
                Log.d("MenuItemRemoteDatasource", "Pedido não encontrado para remoção")
            }
        } catch (exception: Exception) {
            Log.e("MenuItemRemoteDatasource", "Erro ao remover o item do pedido", exception)
        }
    }

    override suspend fun getOrderNumberOfItems(userId: String): Int {
        return try {
            val orderCollection = firestore.collection("order")

            val result = orderCollection
                .whereEqualTo("userId", userId)
                .get()
                .await()

            var totalQuantity = 0
            for (document in result) {
                val order = document.toObject(OrderModel::class.java)
                totalQuantity += order.quantity
            }

            totalQuantity
        } catch (exception: Exception) {
            Log.e("MenuItemRemoteDatasource", "Erro ao buscar e somar os itens do pedido", exception)
            0
        }
    }

    override suspend fun getOrderItems(userId: String): List<OrderModel> {
        return try {
            val result = firestore.collection("order")
                .whereEqualTo("userId", userId)
                .get()
                .await()

            val menuIds = mutableListOf<OrderModel>()

            for (document in result) {
                val order = document.toObject(OrderModel::class.java)
                menuIds.add(order)
            }

            menuIds
        } catch (exception: Exception) {
            Log.e("MenuItemRemoteDatasource", "Erro ao buscar os itens do pedido", exception)
            emptyList()
        }
    }

}
