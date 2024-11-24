package com.example.kotlinfoodorder.login.data

import com.example.kotlinfoodorder.menu.ui.menu.MenuItem

interface MenuItemRepository {
    suspend fun getMenuItems(): List<MenuItem>
    suspend fun getMenuItem(id: String): MenuItem?
}