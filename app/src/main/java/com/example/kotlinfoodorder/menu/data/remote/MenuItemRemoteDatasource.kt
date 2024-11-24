package com.example.kotlinfoodorder.menu.data.remote

import com.example.kotlinfoodorder.menu.ui.menu.MenuItem

interface MenuItemRemoteDatasource {
    suspend fun getMenuItems(): List<MenuItem>
    suspend fun getMenuItem(id: String): MenuItem?
}