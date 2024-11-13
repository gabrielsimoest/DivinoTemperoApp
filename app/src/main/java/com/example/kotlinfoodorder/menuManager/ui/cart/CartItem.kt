package com.example.kotlinfoodorder.menuManager.ui.menu

data class CartItem(
    val name: String,
    val description: String,
    val price: Double,
    val imageResId: Int,
    var quantity: Int = 1
)
