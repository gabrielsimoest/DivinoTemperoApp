package com.example.kotlinfoodorder.menu.data.repository

import android.R
import com.example.kotlinfoodorder.login.data.MenuItemRepository
import com.example.kotlinfoodorder.menu.data.remote.MenuItemRemoteDatasource
import com.example.kotlinfoodorder.menu.ui.menu.MenuItem
import com.google.firebase.firestore.FirebaseFirestore

class MenuItemRepositoryImpl(
    private val menuItemRemoteDatasource: MenuItemRemoteDatasource
): MenuItemRepository {
    override suspend fun getMenuItems(): List<MenuItem> {
        return menuItemRemoteDatasource.getMenuItems()
    }

    override suspend fun getMenuItem(id: String): MenuItem? {
//        return inMemoryTempList.find { it.id == id } ?: throw NoSuchElementException("Item with ID $id not found.")
        return menuItemRemoteDatasource.getMenuItem(id)
    }
}