package com.example.kotlinfoodorder.menu.data.remote

import android.util.Log
import com.example.kotlinfoodorder.menu.model.MenuCategoryModel
import com.example.kotlinfoodorder.order.model.OrderItem
import com.example.kotlinfoodorder.order.model.OrderModel
import com.example.kotlinfoodorder.menu.model.MenuItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class MenuCategoryRemoteDatasourceImpl(
    private val firestore: FirebaseFirestore
) : MenuCategoryRemoteDatasource {
    override suspend fun getMenuCategories(): List<MenuCategoryModel> {
        return try {
            val result = firestore.collection("category").get().await()

            val menuItems = mutableListOf<MenuCategoryModel>()

            for (document in result) {
                val menuItem = document.toObject(MenuCategoryModel::class.java)
                menuItems.add(menuItem)
            }

            menuItems
        } catch (exception: Exception) {
            emptyList()
        }
    }
}
