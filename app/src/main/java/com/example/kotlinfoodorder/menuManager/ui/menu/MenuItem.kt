package com.example.kotlinfoodorder.menuManager.ui.menu

data class MenuItem(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageResId: Int
)
