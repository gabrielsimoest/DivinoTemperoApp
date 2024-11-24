package com.example.kotlinfoodorder.menu.data.remote

import android.util.Log
import com.example.kotlinfoodorder.menu.ui.menu.MenuItem
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

}
