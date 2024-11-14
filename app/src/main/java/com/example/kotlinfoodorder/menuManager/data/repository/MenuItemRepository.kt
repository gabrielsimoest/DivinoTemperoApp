package com.example.kotlinfoodorder.login.data

import com.example.kotlinfoodorder.login.model.User
import com.example.kotlinfoodorder.menuManager.ui.menu.MenuItem

interface MenuItemRepository {
    suspend fun getMenuItems(): List<MenuItem>
}